package com.alperbry.buildtrackerclient.firebase.network.service

import com.alperbry.buildtrackerclient.firebase.di.DispatcherProvider
import com.alperbry.buildtrackerclient.firebase.model.firestore.authentication.AuthenticationDTO
import com.alperbry.buildtrackerclient.firebase.model.firestore.authentication.UserDTO
import com.alperbry.buildtrackerclient.firebase.network.common.Response
import com.alperbry.buildtrackerclient.firebase.network.common.networkCall
import com.alperbry.buildtrackerclient.firebase.network.jsonBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.withContext

internal interface AuthenticationService {

    suspend fun signIn(user: UserDTO): Response<AuthenticationDTO>
}

internal class AuthenticationServiceImpl(
    private val httpClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider
) : AuthenticationService {

    override suspend fun signIn(user: UserDTO) = withContext(dispatcherProvider.io) {
        httpClient.networkCall {
            post(
                "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword"
            ) {
                jsonBody {
                    setBody(user)
                }
            }.body<AuthenticationDTO>()
        }
    }
}
