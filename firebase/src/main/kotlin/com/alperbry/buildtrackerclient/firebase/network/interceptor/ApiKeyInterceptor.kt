package com.alperbry.buildtrackerclient.firebase.network.interceptor

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter

internal class ApiKeyInterceptor(
    private val apiKey: String
) : HttpRequestInterceptor {

    override suspend fun intercept(request: HttpRequestBuilder): HttpRequestBuilder {
        request.parameter("key", apiKey)
        return request
    }
}
