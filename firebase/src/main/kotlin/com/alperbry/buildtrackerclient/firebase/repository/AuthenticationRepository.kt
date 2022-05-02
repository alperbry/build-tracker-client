package com.alperbry.buildtrackerclient.firebase.repository

import com.alperbry.buildtrackerclient.firebase.model.firestore.Authentication
import com.alperbry.buildtrackerclient.firebase.model.firestore.authentication.UserDTO
import com.alperbry.buildtrackerclient.firebase.model.firestore.toAuthentication
import com.alperbry.buildtrackerclient.firebase.network.common.Response
import com.alperbry.buildtrackerclient.firebase.network.common.fold
import com.alperbry.buildtrackerclient.firebase.network.service.AuthenticationService

internal interface AuthenticationRepository {

    suspend fun signIn(email: String, password: String): Response<Authentication>
}

internal class AuthenticationRepositoryImpl(
    private val authenticationService: AuthenticationService
) : AuthenticationRepository {

    override suspend fun signIn(email: String, password: String): Response<Authentication> {
        return authenticationService.signIn(
            UserDTO(email, password, true)
        ).fold(
            onSuccess = { Response.Success(it.toAuthentication()) },
            onFailure = { it }
        )
    }
}
