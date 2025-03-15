package com.tonyxlab.echojournal.domain.repository

import com.tonyxlab.echojournal.domain.model.Topic
import kotlinx.coroutines.flow.Flow


interface TopicRepository {


    fun getTopics(): Flow<List<Topic>>

    suspend fun searchTopics(query: String): List<Topic>

    suspend fun insertTopic(topic: Topic)

    suspend fun deleteTopic(topic: Topic)

    suspend fun getTopicsByIds(id: List<Long>): List<Topic>
}
