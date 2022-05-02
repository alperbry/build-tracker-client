package com.alperbry.buildtrackerclient.firebase.model

import com.alperbry.buildtrackerclient.model.HardwareMetadata
import com.alperbry.buildtrackerclient.model.OperatingSystemMetadata
import kotlinx.datetime.Instant

internal data class BuildInfo(
    val projectId: String,
    val stateIdentifier: String,
    val durationInMs: Long,
    val hardwareMetadata: HardwareMetadata,
    val operatingSystemMetadata: OperatingSystemMetadata,
    val outputs: List<Map<String, Any>>,
    val timestamp: Instant
)
