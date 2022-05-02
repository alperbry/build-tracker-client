package com.alperbry.buildtrackerclient.firebase.model.firestore.authentication

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class UserDTO(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
    @SerialName("returnSecureToken") val returnSecureToken: Boolean
)
