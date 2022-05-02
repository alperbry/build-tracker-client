package com.alperbry.buildtrackerclient.firebase.di

import com.alperbry.buildtrackerclient.firebase.network.service.BuildInfoServiceImpl
import com.alperbry.buildtrackerclient.firebase.repository.BuildInfoRepository
import com.alperbry.buildtrackerclient.firebase.repository.BuildInfoRepositoryImpl

internal interface BuildInfoStorageProvider {

    fun buildInfoRepository(): BuildInfoRepository
}

internal class BuildInfoStorageProviderImpl(
    private val httpClientProvider: HttpClientProvider,
    private val dispatcherProvider: DispatcherProvider,
    private val databaseId: String
) : BuildInfoStorageProvider {

    override fun buildInfoRepository(): BuildInfoRepository {
        return BuildInfoRepositoryImpl(
            BuildInfoServiceImpl(
                databaseId,
                httpClientProvider.client(),
                dispatcherProvider
            )
        )
    }
}
