package com.alperbry.buildtrackerclient.firebase.model.firestore.build

import com.alperbry.buildtrackerclient.firebase.util.serialization.FirestoreTextFieldSerializer
import kotlinx.serialization.Serializable

@Serializable
internal data class OperatingSystemMetadataDTO(
    @Serializable(FirestoreTextFieldSerializer::class)
    val name: String,

    @Serializable(FirestoreTextFieldSerializer::class)
    val architecture: String,

    @Serializable(FirestoreTextFieldSerializer::class)
    val version: String
)
