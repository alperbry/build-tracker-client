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

internal object FirestoreTextFieldSerializer : KSerializer<String> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("textFieldSerializer") {
        element<String>("stringValue")
    }

    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value)
        }
    }

    override fun deserialize(decoder: Decoder): String {
        return decoder.decodeStructure(descriptor) {
            var text = ""
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> text = decodeStringElement(descriptor, 0)
                    CompositeDecoder.DECODE_DONE -> break
                }
            }
            text
        }
    }
}
