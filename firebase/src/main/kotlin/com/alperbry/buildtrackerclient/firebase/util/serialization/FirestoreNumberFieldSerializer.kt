package com.alperbry.buildtrackerclient.firebase.util.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

internal object FirestoreNumberFieldSerializer : KSerializer<Int> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("numberFieldSerializer") {
        element<Int>("integerValue")
    }

    override fun serialize(encoder: Encoder, value: Int) {
        encoder.encodeStructure(descriptor) {
            encodeIntElement(descriptor, 0, value)
        }
    }

    override fun deserialize(decoder: Decoder): Int {
        return decoder.decodeStructure(descriptor) {
            var number = 0
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> number = decodeIntElement(descriptor, 0)
                    CompositeDecoder.DECODE_DONE -> break
                }
            }
            number
        }
    }
}
