package com.alperbry.buildtrackerclient.firebase.model.firestore.authentication

import kotlinx.serialization.Serializable

@Serializable
internal data class AuthenticationDTO(
    val email: String?,
    val displayName: String?,
    val idToken: String?,
    val registered: Boolean?,
    val refreshToken: String?,
    val expiresIn: String?,
)
