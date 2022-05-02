package com.alperbry.buildtrackerclient.model

data class HardwareMetadata(
    val coreCount: Int,
    val cpuModel: String,
    val environmentIdentifier: String,
    val physicalMemoryInMb: Int
)
