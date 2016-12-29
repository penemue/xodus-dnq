package kotlinx.dnq.link

import com.jetbrains.teamsys.dnq.association.AggregationAssociationSemantics
import com.jetbrains.teamsys.dnq.association.AssociationSemantics
import jetbrains.exodus.query.metadata.AssociationEndCardinality
import jetbrains.exodus.query.metadata.AssociationEndType
import kotlinx.dnq.XdEntity
import kotlinx.dnq.XdEntityType
import kotlinx.dnq.query.XdMutableQuery
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1

class XdManyChildrenToMultiParentLink<R : XdEntity, T : XdEntity>(
        val entityType: XdEntityType<T>,
        override val oppositeField: KProperty1<T, XdMutableQuery<R>>,
        dbPropertyName: String?
) : ReadWriteProperty<R, T?>, XdLink<R, T>(
        entityType,
        dbPropertyName,
        AssociationEndCardinality._0_1,
        AssociationEndType.ChildEnd,
        onDelete = OnDeletePolicy.CLEAR,
        onTargetDelete = OnDeletePolicy.CASCADE
) {

    override fun getValue(thisRef: R, property: KProperty<*>): T? {
        return AssociationSemantics.getToOne(thisRef.entity, property.name)?.let { value ->
            entityType.wrap(value)
        }
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: T?) {
        if (value != null) {
            AggregationAssociationSemantics.setManyToOne(value.entity, oppositeField.name, property.name, thisRef.entity)
        } else {
            AggregationAssociationSemantics.setManyToOne(null, oppositeField.name, property.name, thisRef.entity)
        }
    }

    override fun isDefined(thisRef: R, property: KProperty<*>) = getValue(thisRef, property) != null
}

