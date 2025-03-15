package com.tonyxlab.echojournal.data.local.mappers

import com.tonyxlab.echojournal.data.local.entity.TopicEntity
import com.tonyxlab.echojournal.domain.model.Topic


fun Topic.toEntity(): TopicEntity {

    return TopicEntity(
        id = id,
        name = name,
    )
}

fun TopicEntity.toModel(): Topic {

    return Topic(
        id = id,
        name = name,
    )
}

fun List<TopicEntity>.toTopicsList(): List<Topic> = map { it.toModel() }