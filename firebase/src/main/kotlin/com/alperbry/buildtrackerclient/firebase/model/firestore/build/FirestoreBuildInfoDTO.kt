package com.alperbry.buildtrackerclient.firebase.model.firestore.build

import com.alperbry.buildtrackerclient.firebase.model.FirestoreList
import com.alperbry.buildtrackerclient.firebase.model.FirestorePOJO
import com.alperbry.buildtrackerclient.firebase.model.FirestoreSumType
import com.alperbry.buildtrackerclient.firebase.util.serialization.FirestoreNumberFieldSerializer
import com.alperbry.buildtrackerclient.firebase.util.serialization.FirestoreTextFieldSerializer
import com.alperbry.buildtrackerclient.firebase.util.serialization.FirestoreTimestampFieldSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FirestoreBuildInfoDTO(

    @Serializable(FirestoreTextFieldSerializer::class)
    @SerialName("project_id")
    val projectId: String,

    @Serializable(FirestoreTextFieldSerializer::class)
    @SerialName("state_identifier")
    val stateIdentifier: String,

    @Serializable(FirestoreNumberFieldSerializer::class)
    @SerialName("duration_in_ms")
    val durationInMs: Long,

    @SerialName("hardware")
    val hardwareMetadata: FirestorePOJO<HardwareMetadataDTO>,

    @SerialName("operating_system")
    val operatingSystemMetadata: FirestorePOJO<OperatingSystemMetadataDTO>,

    val outputs: FirestoreList<FirestorePOJO<Map<String, FirestoreSumType>>>,

    @Serializable(FirestoreTimestampFieldSerializer::class)
    val timestamp: Instant
)
