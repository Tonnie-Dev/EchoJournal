package com.tonyxlab.echojournal.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Upsert


@Dao
interface BaseDao<T> {

    @Upsert
    suspend fun upsert(value:T)

    @Delete
    suspend fun delete(value: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: T)


}
