package com.tonyxlab.echojournal.data.local.mappers

import com.tonyxlab.echojournal.data.local.entity.EchoEntity
import com.tonyxlab.echojournal.domain.model.Echo

fun Echo.toEchoEntity(): EchoEntity {
    return EchoEntity(
        id = id,
        title = title,
        mood = mood,
        audioFilePath = audioFilePath,
        audioDuration = audioDuration,
        description = description,
        topics = topics,
        creationTimestamp = creationTimestamp,
    )
}

fun EchoEntity.toModel(): Echo {
    return Echo(
        id = id,
        title = title,
        mood = mood,
        audioFilePath = audioFilePath,
        audioDuration = audioDuration,
        description = description,
        topics = topics,
        creationTimestamp = creationTimestamp,
    )
}

fun List<EchoEntity>.toEchoesList(): List<Echo> = this.map { it.toModel() }
