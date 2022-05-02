package com.alperbry.buildtrackerclient.firebase.model.firestore.build

import com.alperbry.buildtrackerclient.firebase.util.serialization.FirestoreNumberFieldSerializer
import com.alperbry.buildtrackerclient.firebase.util.serialization.FirestoreTextFieldSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class HardwareMetadataDTO(
    @Serializable(FirestoreNumberFieldSerializer::class)
    @SerialName("core_count")
    val coreCount: Int,

    @Serializable(FirestoreTextFieldSerializer::class)
    @SerialName("cpu_model")
    val cpuModel: String,

    @Serializable(FirestoreTextFieldSerializer::class)
    @SerialName("environment_identifier")
    val environmentIdentifier: String,

    @Serializable(FirestoreNumberFieldSerializer::class)
    @SerialName("physical_memory_in_mb")
    val physicalMemoryInMb: Int
)
