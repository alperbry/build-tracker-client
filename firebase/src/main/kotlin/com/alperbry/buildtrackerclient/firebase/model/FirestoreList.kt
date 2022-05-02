package com.alperbry.buildtrackerclient.firebase.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class FirestoreList <T> private constructor(
    @SerialName("arrayValue")
    private val firestoreList: FirestoreInnerList<T>
){

    constructor(value: List<T>) : this(
        FirestoreInnerList(value)
    )

    val value: List<T>
        get() = firestoreList.value

    @Serializable
    private data class FirestoreInnerList <T> (


        // todo buraya serialize lazım custom
        @SerialName("values")
        val value: List<T>
    )

}

internal fun <T> List<T>.asFirestoreList() = FirestoreList(this)
