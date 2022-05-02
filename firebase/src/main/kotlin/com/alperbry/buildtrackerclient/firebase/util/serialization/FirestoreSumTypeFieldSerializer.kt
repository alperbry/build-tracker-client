package com.alperbry.buildtrackerclient.firebase.util.serialization

import com.alperbry.buildtrackerclient.firebase.model.FirestoreSumType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

internal object FirestoreSumTypeFieldSerializer : KSerializer<FirestoreSumType> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("sumTypeFieldSerializer") {
        element<Int>("integerValue")
        element<String>("stringValue")
    }

    override fun serialize(encoder: Encoder, value: FirestoreSumType) {
        encoder.encodeStructure(descriptor) {
            when (value) {
                is FirestoreSumType.Number -> encodeIntElement(descriptor, 0, value.value)
                is FirestoreSumType.Text -> encodeStringElement(descriptor, 1, value.value)
            }
        }
    }

    override fun deserialize(decoder: Decoder): FirestoreSumType {
        return decoder.decodeStructure(descriptor) {
            var number = 0 // todo will be used
            var text = ""
            while (true) { // fixme
                when (val index = decodeElementIndex(FirestoreNumberFieldSerializer.descriptor)) {
                    0 -> {
                        text = decodeStringElement(descriptor, 1)
                    }
                    CompositeDecoder.DECODE_DONE -> break
                }
            }
            FirestoreSumType.Text(text)
        }
    }
}
