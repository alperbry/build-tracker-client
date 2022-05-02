package com.alperbry.buildtrackerclient.firebase.domain.authentication

import com.alperbry.buildtrackerclient.firebase.model.firestore.Authentication
import com.alperbry.buildtrackerclient.firebase.network.common.fold
import com.alperbry.buildtrackerclient.firebase.network.common.onFailure
import com.alperbry.buildtrackerclient.firebase.network.common.onSuccess
import com.alperbry.buildtrackerclient.firebase.repository.AuthenticationRepository

internal interface Authenticator {

    suspend fun authenticate(
        email: String,
        password: String
    ): Result<String>
}

internal class AuthenticatorImpl(
    private val authenticationRepository: AuthenticationRepository
) : Authenticator {

    // todo check for expiration and other stuff

    override suspend fun authenticate(
        email: String,
        password: String
    ): Result<String> {
        val token = authenticationRepository.signIn(email, password)
            .onSuccess {
                // todo cache the token
            }
            .onFailure {
                // todo log
            }
            .fold(
                onSuccess = Authentication::idToken,
                onFailure = { "" } // fixme
            )

        return if (token.isNotEmpty()) Result.success(token) else Result.failure(IllegalArgumentException("Credentials are invalid."))
    }
}
