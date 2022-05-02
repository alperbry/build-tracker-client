package com.alperbry.buildtrackerclient.firebase.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal fun <T> T.asFirestorePOJO() = FirestorePOJO(this)

@Serializable
internal class FirestorePOJO<T> private constructor(
    @SerialName("mapValue")
    private val firestorePOJO: FirestoreInnerPOJO<T>
) {
    constructor(value: T) : this(
        FirestoreInnerPOJO(value)
    )

    val value: T
        get() = firestorePOJO.value

    @Serializable
    private data class FirestoreInnerPOJO<T>(

        @SerialName("fields")
        val value: T
    )
}
