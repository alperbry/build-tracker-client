package com.alperbry.buildtrackerclient.firebase.network.interceptor

import io.ktor.client.request.HttpRequestBuilder

internal fun interface HttpRequestInterceptor {

    suspend fun intercept(request: HttpRequestBuilder): HttpRequestBuilder
}
