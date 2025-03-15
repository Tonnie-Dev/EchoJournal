package com.tonyxlab.echojournal.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.tonyxlab.echojournal.data.database.entity.TopicEntity
import com.tonyxlab.echojournal.domain.model.Topic
import kotlinx.coroutines.flow.Flow


@Dao
interface TopicDao : BaseDao<TopicEntity> {

    @Query("SELECT * FROM topics_table ORDER BY name ASC")
    fun getTopics(): Flow<List<TopicEntity>>

    @Query("SELECT * FROM topics_table WHERE id IN (:topicIds)")
    suspend fun getTopicsById(topicIds: List<Long>): List<TopicEntity>

    @Query("SELECT * FROM topics_table WHERE name LIKE '%' || :query || '%'")
    suspend fun matchTopics(query: String): List<TopicEntity>


}