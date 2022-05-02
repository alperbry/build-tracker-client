package com.alperbry.buildtrackerclient.firebase.client

import com.alperbry.buildtrackerclient.firebase.di.AuthenticationProvider
import com.alperbry.buildtrackerclient.firebase.di.AuthenticationProviderImpl
import com.alperbry.buildtrackerclient.firebase.di.BuildInfoStorageProviderImpl
import com.alperbry.buildtrackerclient.firebase.di.DispatcherProvider
import com.alperbry.buildtrackerclient.firebase.di.DispatcherProviderImpl
import com.alperbry.buildtrackerclient.firebase.di.HttpClientProviderImpl
import com.alperbry.buildtrackerclient.firebase.network.interceptor.ApiKeyInterceptor
import com.alperbry.buildtrackerclient.firebase.network.interceptor.AuthenticationInterceptor
import com.alperbry.buildtrackerclient.firebase.util.TimestampGeneratorImpl
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.GlobalScope

object BuildTrackerClientProvider {

    private val dispatcherProvider: DispatcherProvider = DispatcherProviderImpl()

    private val authenticationProvider: AuthenticationProvider = AuthenticationProviderImpl(dispatcherProvider)

    fun client(
        email: String,
        password: String,
        apiKey: String,
        databaseId: String
    ): FirebaseBuildTrackerClient {

        if (listOf(email, password, apiKey, databaseId).any(String::isEmpty)) {
            throw IllegalArgumentException("Provided Firebase credentials should not be empty.")
        }

        val apiKeyInterceptor = ApiKeyInterceptor(apiKey)

        val authenticationClientProvider = HttpClientProviderImpl(
            CIO,
            listOf(apiKeyInterceptor)
        )

        val buildTrackerRequestInterceptors = listOf(
            AuthenticationInterceptor(
                authenticationProvider,
                authenticationClientProvider.client(),
                email, password
            ),
            apiKeyInterceptor
        )

        val httpClientProvider = HttpClientProviderImpl(
            CIO,
            buildTrackerRequestInterceptors
        )

        val buildInfoStorageProvider = BuildInfoStorageProviderImpl(httpClientProvider, dispatcherProvider, databaseId)

        return FirebaseBuildTrackerClient(
            buildInfoStorageProvider.buildInfoRepository(),
            TimestampGeneratorImpl(),
            GlobalScope // fixme
        )
    }
}
