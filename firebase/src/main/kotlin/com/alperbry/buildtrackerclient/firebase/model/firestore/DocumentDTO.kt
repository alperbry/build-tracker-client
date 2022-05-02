package com.alperbry.buildtrackerclient.firebase.model.firestore

import com.alperbry.buildtrackerclient.firebase.model.firestore.build.FirestoreBuildInfoDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class DocumentDTO(
    @SerialName("name") val name: String? = null,
    @SerialName("fields") val fields: FirestoreBuildInfoDTO,
    @SerialName("createTime") val createTime: String? = null,
    @SerialName("updateTime") val updateTime: String? = null
)
