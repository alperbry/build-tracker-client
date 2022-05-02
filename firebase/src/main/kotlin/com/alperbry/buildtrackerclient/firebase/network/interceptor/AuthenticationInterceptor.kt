package com.alperbry.buildtrackerclient.firebase.network.interceptor

import com.alperbry.buildtrackerclient.firebase.di.AuthenticationProvider
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.bearerAuth
import io.ktor.http.HttpHeaders

internal class AuthenticationInterceptor(
    private val authenticationProvider: AuthenticationProvider,
    private val client: HttpClient,
    private val email: String,
    private val password: String
) : HttpRequestInterceptor {


    @Synchronized override suspend fun intercept(request: HttpRequestBuilder): HttpRequestBuilder {
        val authenticator = authenticationProvider.authenticator(client)

        if (request.headers[HttpHeaders.Authorization] == null) {
            val tokenResult = authenticator.authenticate(email, password)

            tokenResult.onSuccess { token ->
                request.bearerAuth(token)
            }
        }

        return request
    }
}
