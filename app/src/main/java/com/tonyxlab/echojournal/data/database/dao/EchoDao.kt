package com.tonyxlab.echojournal.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.tonyxlab.echojournal.data.database.entity.EchoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EchoDao:BaseDao<EchoEntity> {

    @Query("SELECT * FROM echo_table ORDER BY timestamp DESC")
    fun getEchos():Flow<List<EchoEntity>>


    @Query("SELECT * FROM echo_table WHERE id=:id")
    fun getEchoById(id:String): EchoEntity?

}