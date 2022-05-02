package com.alperbry.buildtrackerclient.firebase.di

import com.alperbry.buildtrackerclient.firebase.domain.authentication.Authenticator
import com.alperbry.buildtrackerclient.firebase.domain.authentication.AuthenticatorImpl
import com.alperbry.buildtrackerclient.firebase.network.service.AuthenticationServiceImpl
import com.alperbry.buildtrackerclient.firebase.repository.AuthenticationRepositoryImpl
import io.ktor.client.HttpClient

internal interface AuthenticationProvider {

    fun authenticator(client: HttpClient): Authenticator
}

internal class AuthenticationProviderImpl(
    private val dispatcherProvider: DispatcherProvider
) : AuthenticationProvider {

    override fun authenticator(client: HttpClient): Authenticator {
        return AuthenticatorImpl(AuthenticationRepositoryImpl(AuthenticationServiceImpl(client, dispatcherProvider)))
    }
}
