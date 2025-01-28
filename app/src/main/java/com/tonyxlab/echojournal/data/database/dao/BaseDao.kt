package com.tonyxlab.echojournal.data.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update


interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(value: T): Long

    @Delete
    fun delete(value: T): Int

    @Update
    fun update(value: T)
}
