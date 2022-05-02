package com.alperbry.buildtrackerclient.firebase.util.serialization

import kotlinx.datetime.Instant
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

internal object FirestoreTimestampFieldSerializer : KSerializer<Instant> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("timestampFieldSerializer") {
        element<Instant>("timestampValue")
    }

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, Instant.serializer(), value)
        }
    }

    override fun deserialize(decoder: Decoder): Instant {
        return decoder.decodeStructure(descriptor) {
            var instant: Instant? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> {
                        instant = decodeSerializableElement(Instant.serializer().descriptor, 0, Instant.serializer())
                    }
                    CompositeDecoder.DECODE_DONE -> break
                }
            }

            instant!!
        }
    }
}
