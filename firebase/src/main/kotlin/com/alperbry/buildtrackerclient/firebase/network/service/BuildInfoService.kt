package com.alperbry.buildtrackerclient.firebase.network.service

import com.alperbry.buildtrackerclient.firebase.di.DispatcherProvider
import com.alperbry.buildtrackerclient.firebase.model.firestore.DocumentDTO
import com.alperbry.buildtrackerclient.firebase.model.firestore.build.FirestoreBuildInfoDTO
import com.alperbry.buildtrackerclient.firebase.network.common.Response
import com.alperbry.buildtrackerclient.firebase.network.common.networkCall
import com.alperbry.buildtrackerclient.firebase.network.jsonBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.withContext

internal interface BuildInfoService {

    suspend fun newBuild(buildInfo: FirestoreBuildInfoDTO): Response<DocumentDTO>
}

internal class BuildInfoServiceImpl(
    private val databaseId: String,
    private val httpClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider
) : BuildInfoService {

    override suspend fun newBuild(buildInfo: FirestoreBuildInfoDTO) = withContext(dispatcherProvider.io) {
        httpClient.networkCall {
            post(
                "https://firestore.googleapis.com/v1beta1"
            ) {
                url {
                    appendPathSegments( // fixme url generation
                        "projects",
                        databaseId,
                        "databases",
                        "(default)",
                        "documents",
                        "build",
                    )
                }
                jsonBody {
                    setBody(
                        DocumentDTO(fields = buildInfo)
                    )
                }
            }.body<DocumentDTO>()
        }
    }
}
