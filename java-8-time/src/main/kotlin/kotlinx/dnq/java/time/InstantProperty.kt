/**
 * Copyright 2006 - 2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kotlinx.dnq.java.time

import jetbrains.exodus.util.LightOutputStream
import kotlinx.dnq.XdEntity
import kotlinx.dnq.simple.Constraints
import kotlinx.dnq.simple.DEFAULT_REQUIRED
import kotlinx.dnq.simple.custom.type.*
import kotlinx.dnq.simple.xdCachedProp
import kotlinx.dnq.simple.xdNullableCachedProp
import java.io.ByteArrayInputStream
import java.time.Instant

object InstantBinding : XdCustomTypeBinding<Instant>() {
    override fun write(stream: LightOutputStream, value: Instant) {
        stream.writeLong(value.epochSecond)
        stream.writeInt(value.nano)
    }

    override fun read(stream: ByteArrayInputStream): Instant {
        return Instant.ofEpochSecond(
                stream.readLong(), stream.readInt().toLong()
        )
    }
}

fun <R : XdEntity> xdInstantProp(dbName: String? = null, constraints: Constraints<R, Instant?>? = null) =
        xdNullableCachedProp(dbName, InstantBinding, constraints)

fun <R : XdEntity> xdRequiredInstantProp(unique: Boolean = false, dbName: String? = null, constraints: Constraints<R, Instant?>? = null) =
        xdCachedProp(dbName, constraints, true, unique, InstantBinding, DEFAULT_REQUIRED)
