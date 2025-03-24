package com.tonyxlab.echojournal.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tonyxlab.echojournal.data.local.dao.TopicsDao
import com.tonyxlab.echojournal.data.local.entity.TopicEntity


@Database(entities = [TopicEntity::class], version = 1, exportSchema = false)
abstract class TopicsDatabase: RoomDatabase(){

    abstract fun getTopicsDao(): TopicsDao

}
