package com.tonyxlab.echojournal.data.mappers

import android.net.Uri
import com.tonyxlab.echojournal.data.database.entity.EchoEntity
import com.tonyxlab.echojournal.domain.model.Echo


fun Echo.toEntityModel(): EchoEntity {
    return EchoEntity(
        id = id,
        name = name,
        description = description,
        timestamp = timestamp,
        length = length,
        mood = mood,
        topics = topics,
        uri = uri.toString()
    )
}

fun EchoEntity.toDomainModel(): Echo {
    return Echo(
        id = id,
        name = name,
        description = description,
        timestamp = timestamp,
        length = length,
        mood = mood,
        topics = topics,
        uri = Uri.parse(uri)
    )
}
