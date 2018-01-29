package com.jetbrains.teamsys.dnq.database

import jetbrains.exodus.database.TransientStoreSession
import jetbrains.exodus.entitystore.Entity
import jetbrains.exodus.entitystore.EntityIterable
import jetbrains.exodus.entitystore.PersistentStoreTransaction
import java.util.*

internal interface SessionQueryMixin : TransientStoreSession {
    val persistentTransactionInternal: PersistentStoreTransaction

    fun wrap(action: String, entityIterable: EntityIterable): EntityIterable

    override fun getAll(entityType: String): EntityIterable {
        return wrap("getAll", persistentTransactionInternal.getAll(entityType))
    }

    override fun getSingletonIterable(entity: Entity): EntityIterable {
        return wrap("getSingletonIterable", persistentTransactionInternal.getSingletonIterable(
                (entity as TransientEntityImpl).persistentEntity
        ))
    }

    override fun find(entityType: String, propertyName: String, value: Comparable<*>): EntityIterable {
        return wrap("find", persistentTransactionInternal.find(
                entityType,
                propertyName,
                value
        ))
    }

    override fun find(entityType: String, propertyName: String, minValue: Comparable<*>, maxValue: Comparable<*>): EntityIterable {
        return wrap("find", persistentTransactionInternal.find(
                entityType,
                propertyName,
                minValue,
                maxValue
        ))
    }

    override fun findIds(entityType: String, minValue: Long, maxValue: Long): EntityIterable {
        return wrap("findIds", persistentTransactionInternal.findIds(
                entityType,
                minValue,
                maxValue
        ))
    }

    override fun findWithProp(entityType: String, propertyName: String): EntityIterable {
        return wrap("findWithProp", persistentTransactionInternal.findWithProp(
                entityType,
                propertyName
        ))
    }

    override fun findStartingWith(entityType: String, propertyName: String, value: String): EntityIterable {
        return wrap("startsWith", persistentTransactionInternal.findStartingWith(
                entityType,
                propertyName,
                value
        ))
    }

    override fun findWithBlob(entityType: String, propertyName: String): EntityIterable {
        return wrap("findWithBlob", persistentTransactionInternal.findWithBlob(
                entityType,
                propertyName
        ))
    }

    override fun findLinks(entityType: String, entity: Entity, linkName: String): EntityIterable {
        return wrap("findLinks", persistentTransactionInternal.findLinks(
                entityType,
                entity,
                linkName
        ))
    }

    override fun findLinks(entityType: String, entities: EntityIterable, linkName: String): EntityIterable {
        return wrap("findLinks", persistentTransactionInternal.findLinks(
                entityType,
                entities,
                linkName
        ))
    }

    override fun findWithLinks(entityType: String, linkName: String): EntityIterable {
        return wrap("findWithLinks", persistentTransactionInternal.findWithLinks(
                entityType,
                linkName
        ))
    }

    override fun findWithLinks(entityType: String,
                               linkName: String,
                               oppositeEntityType: String,
                               oppositeLinkName: String): EntityIterable {
        return wrap("findWithLinks", persistentTransactionInternal.findWithLinks(
                entityType,
                linkName,
                oppositeEntityType,
                oppositeLinkName
        ))
    }

    override fun sort(entityType: String,
                      propertyName: String,
                      ascending: Boolean): EntityIterable {
        return wrap("sort", persistentTransactionInternal.sort(
                entityType,
                propertyName,
                ascending
        ))
    }

    override fun sort(entityType: String,
                      propertyName: String,
                      rightOrder: EntityIterable,
                      ascending: Boolean): EntityIterable {
        return wrap("sort", persistentTransactionInternal.sort(
                entityType,
                propertyName,
                rightOrder,
                ascending
        ))
    }

    override fun sortLinks(entityType: String,
                           sortedLinks: EntityIterable,
                           isMultiple: Boolean,
                           linkName: String,
                           rightOrder: EntityIterable): EntityIterable {
        return wrap("sortLinks", persistentTransactionInternal.sortLinks(
                entityType,
                sortedLinks,
                isMultiple,
                linkName,
                rightOrder
        ))
    }

    override fun sortLinks(entityType: String,
                           sortedLinks: EntityIterable,
                           isMultiple: Boolean,
                           linkName: String,
                           rightOrder: EntityIterable,
                           oppositeEntityType: String,
                           oppositeLinkName: String): EntityIterable {
        return wrap("sortLinks", persistentTransactionInternal.sortLinks(
                entityType,
                sortedLinks,
                isMultiple,
                linkName,
                rightOrder,
                oppositeEntityType,
                oppositeLinkName
        ))
    }

    override fun mergeSorted(sorted: List<EntityIterable>, comparator: Comparator<Entity>): EntityIterable {
        return wrap("mergeSorted", persistentTransactionInternal.mergeSorted(
                sorted,
                comparator
        ))
    }
}