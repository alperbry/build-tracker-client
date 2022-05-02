package com.alperbry.buildtrackerclient.firebase.network

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal inline fun HttpRequestBuilder.jsonBody(block: HttpRequestBuilder.() -> Unit): HttpRequestBuilder {
    contentType(ContentType.Application.Json)
    this.block()
    return this
}
