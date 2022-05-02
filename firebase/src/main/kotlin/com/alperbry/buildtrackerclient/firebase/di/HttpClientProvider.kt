package com.alperbry.buildtrackerclient.firebase.di

import com.alperbry.buildtrackerclient.firebase.network.interceptor.HttpRequestInterceptor
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.cio.CIOEngineConfig
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface HttpClientProvider {

    fun client(): HttpClient
}

internal class HttpClientProviderImpl(
    private val engineFactory: HttpClientEngineFactory<CIOEngineConfig>,
    private val requestInterceptors: List<HttpRequestInterceptor>
) : HttpClientProvider {

    private val client by lazy {
        val client =  HttpClient(engineFactory) {

            expectSuccess = true

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            engine {
                endpoint {
                    connectTimeout = 5_000
                    socketTimeout = 5_000
                    connectAttempts = 2
                }
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }

            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 2)
                exponentialDelay()
            }
        }

        requestInterceptors.forEach { interceptor ->
            client.plugin(HttpSend).intercept { request ->
                val interceptedRequest = interceptor.intercept(request)
                execute(interceptedRequest)
            }
        }

        client
    }

    override fun client(): HttpClient {
        return client
    }
}
