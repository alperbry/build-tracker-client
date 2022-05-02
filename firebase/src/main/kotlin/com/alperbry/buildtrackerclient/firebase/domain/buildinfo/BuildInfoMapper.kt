package com.alperbry.buildtrackerclient.firebase.domain.buildinfo

import com.alperbry.buildtrackerclient.firebase.model.BuildInfo
import com.alperbry.buildtrackerclient.firebase.model.FirestoreList
import com.alperbry.buildtrackerclient.firebase.model.FirestorePOJO
import com.alperbry.buildtrackerclient.firebase.model.FirestoreSumType
import com.alperbry.buildtrackerclient.firebase.model.asFirestoreList
import com.alperbry.buildtrackerclient.firebase.model.asFirestorePOJO
import com.alperbry.buildtrackerclient.firebase.model.asFirestoreSumType
import com.alperbry.buildtrackerclient.firebase.model.firestore.build.FirestoreBuildInfoDTO
import com.alperbry.buildtrackerclient.firebase.model.firestore.build.HardwareMetadataDTO
import com.alperbry.buildtrackerclient.firebase.model.firestore.build.OperatingSystemMetadataDTO
import com.alperbry.buildtrackerclient.model.HardwareMetadata
import com.alperbry.buildtrackerclient.model.OperatingSystemMetadata

internal fun BuildInfo.toFirestoreDTO(): FirestoreBuildInfoDTO {
    return FirestoreBuildInfoDTO(
        projectId = projectId,
        stateIdentifier = stateIdentifier,
        durationInMs = durationInMs,
        hardwareMetadata = hardwareMetadata.toFirestoreDTO(),
        operatingSystemMetadata = operatingSystemMetadata.toFirestoreDTO(),
        outputs = outputs.toFirestoreList(),
        timestamp = timestamp
    )
}

internal fun HardwareMetadata.toFirestoreDTO(): FirestorePOJO<HardwareMetadataDTO> {
    return HardwareMetadataDTO(
        coreCount, cpuModel, environmentIdentifier, physicalMemoryInMb
    ).asFirestorePOJO()
}

internal fun OperatingSystemMetadata.toFirestoreDTO(): FirestorePOJO<OperatingSystemMetadataDTO> {
    return OperatingSystemMetadataDTO(
        name, architecture, version
    ).asFirestorePOJO()
}

internal fun List<Map<String, Any>>.toFirestoreList(): FirestoreList<FirestorePOJO<Map<String, FirestoreSumType>>> {
    val outputList = map { output ->
        output.mapValues {
            it.value.asFirestoreSumType()
        }.asFirestorePOJO()
    }

    return outputList.asFirestoreList()
}
