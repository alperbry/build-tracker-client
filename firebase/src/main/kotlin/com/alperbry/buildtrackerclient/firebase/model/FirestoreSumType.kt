package com.alperbry.buildtrackerclient.firebase.model

import com.alperbry.buildtrackerclient.firebase.util.serialization.FirestoreSumTypeFieldSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(FirestoreSumTypeFieldSerializer::class)
internal sealed class FirestoreSumType {

    abstract val value: Any

    @Serializable
    internal data class Number(@SerialName("integerValue") override val value: Int): FirestoreSumType()

    @Serializable
    internal data class Text(@SerialName("stringValue") override val value: String): FirestoreSumType()
}

internal fun Any.asFirestoreSumType() = when (this) {
    is Int -> FirestoreSumType.Number(this)
    is String -> FirestoreSumType.Text(this)
    else -> FirestoreSumType.Text(toString()) // fixme
}
