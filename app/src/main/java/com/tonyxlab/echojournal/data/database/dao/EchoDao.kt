package com.tonyxlab.echojournal.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.tonyxlab.echojournal.data.database.entity.EchoEntity
import com.tonyxlab.echojournal.data.database.entity.EchoWithTopics
import com.tonyxlab.echojournal.data.database.entity.TopicEntity
import com.tonyxlab.echojournal.data.mappers.toDomainModel
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

/*
@Dao
interface EchoDao:BaseDao<EchoEntity> {

    @Query("SELECT * FROM echo_table ORDER BY timestamp DESC")
    fun getEchos():Flow<List<EchoEntity>>

    @Query("SELECT * FROM echo_table WHERE id=:id")
    fun getEchoById(id:String): EchoEntity?

    @Query("SELECT topics FROM echo_table")
    fun getSavedTopics():Flow<List<String>>

    @Transaction
    @Query("SELECT * FROM echo_table")
    fun getEchoWithTopics():Flow<List<EchoWithTopics>>

}
*/


@Dao
interface EchoDao : BaseDao<EchoEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopic(topic: TopicEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopics(topics: List<TopicEntity>)

    @Query("SELECT * FROM echo_table WHERE id=:id")
    fun getEchoWithTopicsById(id: String): EchoWithTopics?
    @Query("SELECT * FROM topic_table WHERE echo_id = :echoId")
    suspend fun getTopicsByEchoId(echoId: String): List<TopicEntity>

    @Delete
    suspend fun deleteTopic(topic: TopicEntity)


    @Transaction
    @Query("SELECT * FROM echo_table")
    fun getEchoesWithTopics(): Flow<List<EchoWithTopics>>



    @Update
    suspend fun updateTopic(topic: TopicEntity)

    @Transaction
    suspend fun insertEchoWithTopics(echoWithTopics: EchoWithTopics) {
        insert(echoWithTopics.echoEntity)
        insertTopics(echoWithTopics.topicEntities)
    }

    @Transaction
    @Update
    suspend fun updateEchoWithTopics(echoWithTopics: EchoWithTopics) {
        Timber.i("Passed Topics: ${echoWithTopics.topicEntities.size}")

        update(echoWithTopics.echoEntity)

        val existingTopics = getTopicsByEchoId(echoWithTopics.echoEntity.id)

        echoWithTopics.topicEntities.forEach {

            Timber.i("Update called with ${it.topicId}")
            if (it.topicId == 0L) {
                // Insert new topic
                insertTopic(it)
            } else {
                // Update existing topic
                updateTopic(it)
            }
        }

        // Delete removed topics
        val topicsToDelete = existingTopics.filter { existingTopic ->
            echoWithTopics.topicEntities.none { it.topicId == existingTopic.topicId }
        }
        topicsToDelete.forEach { deleteTopic(it) }

    }
}