package com.alperbry.buildtrackerclient

import com.alperbry.buildtrackerclient.model.HardwareMetadata
import com.alperbry.buildtrackerclient.model.OperatingSystemMetadata

interface BuildTrackerClient {

    fun newBuild(
        projectId: String,
        stateIdentifier: String,
        durationInMs: Long,
        hardwareMetadata: HardwareMetadata,
        osMetadata: OperatingSystemMetadata,
        outputInformation: List<Map<String, Any>>
    )
}
