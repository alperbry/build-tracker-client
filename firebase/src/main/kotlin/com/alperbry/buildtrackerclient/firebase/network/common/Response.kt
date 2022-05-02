package com.alperbry.buildtrackerclient.firebase.network.common

import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException

internal sealed class Response<out T> {

    data class Success<T>(val response: T) : Response<T>()

    sealed class Failure : Response<Nothing>() {
        /**
         * Represents server (50x) and client (40x) errors.
         */
        data class HttpFailure(val code: Int, val message: String) : Failure()

        /**
         * Represents IOExceptions and connectivity issues.
         */
        object NetworkFailure : Failure()

        /**
         * Represents SerializationExceptions.
         */
        object SerializationFailure : Failure()
    }
}

internal suspend fun <T> HttpClient.networkCall(block: suspend HttpClient.() -> T): Response<T> {
    return try {
        val response = this.block()
        Response.Success(response)
    } catch (e: ClientRequestException) {
        Response.Failure.HttpFailure(e.response.status.value, e.message)
    } catch (e: ServerResponseException) {
        Response.Failure.HttpFailure(e.response.status.value, e.message)
    } catch (e: IOException) {
        Response.Failure.NetworkFailure
    } catch (e: SerializationException) {
        Response.Failure.SerializationFailure
    }
}

internal inline fun <T> Response<T>.onSuccess(block: (T) -> Unit): Response<T> {
    if (this is Response.Success) {
        block(response)
    }
    return this
}

internal inline fun <T> Response<T>.onFailure(block: (Response.Failure) -> Unit): Response<T> {
    if (this is Response.Failure) {
        block(this)
    }
    return this
}

internal inline fun <T, R> Response<T>.fold(
    onSuccess: (T) -> R,
    onFailure: (Response.Failure) -> R
): R {
    return when (this) {
        is Response.Success -> onSuccess(response)
        is Response.Failure -> onFailure(this)
    }
}
