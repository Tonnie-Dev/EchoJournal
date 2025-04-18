package com.tonyxlab.echojournal.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.tonyxlab.echojournal.data.local.entity.EchoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EchoDao : BaseDao<EchoEntity> {
    @Query("SELECT * FROM echoes_table ORDER BY creation_time_stamp DESC")
    fun getEchoes(): Flow<List<EchoEntity>>
}