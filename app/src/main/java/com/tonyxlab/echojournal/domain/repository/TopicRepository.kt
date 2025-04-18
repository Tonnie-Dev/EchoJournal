package com.tonyxlab.echojournal.domain.repository

import com.tonyxlab.echojournal.domain.model.Topic
import kotlinx.coroutines.flow.Flow

interface TopicRepository {
    fun getTopics(): Flow<List<Topic>>

    suspend fun matchTopics(query: String): List<Topic>

    suspend fun insertTopic(topic: Topic)

    suspend fun deleteTopic(topic: Topic)

    suspend fun getTopicsByIds(topicsIds: List<Long>): List<Topic>
}
