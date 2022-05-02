package com.alperbry.buildtrackerclient.firebase.client

import com.alperbry.buildtrackerclient.BuildTrackerClient
import com.alperbry.buildtrackerclient.firebase.model.BuildInfo
import com.alperbry.buildtrackerclient.firebase.repository.BuildInfoRepository
import com.alperbry.buildtrackerclient.firebase.util.TimestampGenerator
import com.alperbry.buildtrackerclient.model.HardwareMetadata
import com.alperbry.buildtrackerclient.model.OperatingSystemMetadata
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch

class FirebaseBuildTrackerClient internal constructor(
    private val buildInfoRepository: BuildInfoRepository,
    private val timestampGenerator: TimestampGenerator,
    private val coroutineScope: CoroutineScope
) : BuildTrackerClient {

    override fun newBuild(
        projectId: String,
        stateIdentifier: String,
        durationInMs: Long,
        hardwareMetadata: HardwareMetadata,
        osMetadata: OperatingSystemMetadata,
        outputInformation: List<Map<String, Any>>
    ) {
        coroutineScope.launch(NonCancellable) {
            val buildInfo = BuildInfo(
                projectId,
                stateIdentifier,
                durationInMs,
                hardwareMetadata,
                osMetadata,
                outputInformation,
                timestampGenerator.now()
            )
            buildInfoRepository.saveBuild(
                buildInfo
            )
        }
    }
}
