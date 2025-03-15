package com.tonyxlab.echojournal.data.repository

import com.tonyxlab.echojournal.data.local.dao.TopicsDao
import com.tonyxlab.echojournal.data.local.mappers.toEntity
import com.tonyxlab.echojournal.data.local.mappers.toModel
import com.tonyxlab.echojournal.data.local.mappers.toTopicsList
import com.tonyxlab.echojournal.domain.model.Topic
import com.tonyxlab.echojournal.domain.repository.TopicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TopicsRepositoryImpl @Inject constructor(
   private val dao: TopicsDao
): TopicRepository{
    override fun getTopics(): Flow<List<Topic>> {
        return dao.getTopics().map {
            it.toTopicsList()
        }
    }

    override suspend fun searchTopics(query: String): List<Topic> {

        return dao.matchTopics(query = query).toTopicsList()
    }

    override suspend fun insertTopic(topic: Topic) {
        dao.insert(topic.toEntity())
    }

    override suspend fun deleteTopic(topic: Topic) {
        dao.delete(topic.toEntity())
    }

    override suspend fun getTopicsByIds(ids: List<Long>): List<Topic> {

        return dao.getTopicsByIds(ids).map {  it.toModel()}
    }

}