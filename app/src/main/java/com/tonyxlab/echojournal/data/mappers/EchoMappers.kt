package com.tonyxlab.echojournal.data.mappers

import android.net.Uri
import com.tonyxlab.echojournal.data.database.entity.EchoEntity
import com.tonyxlab.echojournal.data.database.entity.EchoWithTopics
import com.tonyxlab.echojournal.data.database.entity.TopicEntity
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

        uri = uri.toString()
    )
}

fun EchoEntity.toDomainModel(topics:List<TopicEntity>): Echo {
    return Echo(
        id = id,
        title = title,
        description = description,
        timestamp = timestamp.fromUtcTimestampToDefaultTimeStamp(),
        length = length,
        mood = mood,
        topics = topics.map { it.topic },
        uri = Uri.parse(uri)
    )
}


fun Echo.toEchoWithTopics(existingTopics: List<TopicEntity>): EchoWithTopics {
    val topicEntities = this.topics.map { topic ->
        val existingTopic = existingTopics.find { it.topic == topic }
        existingTopic?.copy(echoId = id) ?: TopicEntity(echoId = id, topic = topic)
    }
    return EchoWithTopics(echoEntity = this.toEntityModel(), topicEntities = topicEntities)
}



fun EchoWithTopics.toDomainModel(): Echo {

 return echoEntity.toDomainModel(topicEntities)
}
