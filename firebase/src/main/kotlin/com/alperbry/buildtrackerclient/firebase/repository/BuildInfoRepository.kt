package com.alperbry.buildtrackerclient.firebase.repository

import com.alperbry.buildtrackerclient.firebase.domain.buildinfo.toFirestoreDTO
import com.alperbry.buildtrackerclient.firebase.model.BuildInfo
import com.alperbry.buildtrackerclient.firebase.network.common.fold
import com.alperbry.buildtrackerclient.firebase.network.service.BuildInfoService

internal interface BuildInfoRepository {

    suspend fun saveBuild(buildInfo: BuildInfo): Result<Unit>
}

internal class BuildInfoRepositoryImpl(
    private val buildInfoService: BuildInfoService
) : BuildInfoRepository {

    override suspend fun saveBuild(buildInfo: BuildInfo): Result<Unit> {
        return buildInfoService.newBuild(buildInfo.toFirestoreDTO())
            .fold(
                onSuccess = { Result.success(Unit) },
                onFailure = { Result.failure(IllegalStateException()) } // todo log
            )
    }
}
