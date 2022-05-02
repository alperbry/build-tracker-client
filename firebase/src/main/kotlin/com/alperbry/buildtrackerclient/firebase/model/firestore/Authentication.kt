package com.alperbry.buildtrackerclient.firebase.model.firestore

import com.alperbry.buildtrackerclient.firebase.model.firestore.authentication.AuthenticationDTO

internal data class Authentication(
    val idToken: String,
    val refreshToken: String,
    val expiresIn: String
)

internal fun AuthenticationDTO.toAuthentication(): Authentication {
    return Authentication(idToken.orEmpty(), refreshToken.orEmpty(), expiresIn.orEmpty())
}
