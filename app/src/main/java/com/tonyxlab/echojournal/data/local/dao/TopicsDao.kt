package com.tonyxlab.echojournal.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.tonyxlab.echojournal.data.local.entity.TopicEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TopicsDao : BaseDao<TopicEntity> {

    @Query("SELECT * FROM topics_table ORDER BY name ASC")
    fun getTopics(): Flow<List<TopicEntity>>

    @Query("SELECT * FROM topics_table WHERE id IN (:topicIds)")
    suspend fun getTopicsByIds(topicIds: List<Long>): List<TopicEntity>

    @Query("SELECT * FROM topics_table WHERE name LIKE '%' || :query || '%'")
    suspend fun matchTopics(query: String): List<TopicEntity>


}