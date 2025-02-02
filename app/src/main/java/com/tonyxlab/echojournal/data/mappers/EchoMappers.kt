package com.tonyxlab.echojournal.data.mappers

import android.net.Uri
import com.tonyxlab.echojournal.data.database.entity.EchoEntity
import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.utils.fromUtcTimestampToDefaultTimeStamp
import com.tonyxlab.echojournal.utils.toUtcTimeStamp


fun Echo.toEntityModel(): EchoEntity {
    return EchoEntity(
        id = id,
        title = title,
        description = description,
        timestamp = timestamp.toUtcTimeStamp(),
        length = length,
        mood = mood,
        topics = topics,
        uri = uri.toString()
    )
}

fun EchoEntity.toDomainModel(): Echo {
    return Echo(
        id = id,
        title = title,
        description = description,
        timestamp = timestamp.fromUtcTimestampToDefaultTimeStamp(),
        length = length,
        mood = mood,
        topics = topics,
        uri = Uri.parse(uri)
    )
}
