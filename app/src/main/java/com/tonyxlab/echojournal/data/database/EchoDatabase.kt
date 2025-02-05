package com.tonyxlab.echojournal.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tonyxlab.echojournal.data.database.converters.Converters
import com.tonyxlab.echojournal.data.database.dao.EchoDao
import com.tonyxlab.echojournal.data.database.entity.EchoEntity
import com.tonyxlab.echojournal.data.database.entity.TopicEntity

@Database(entities = [EchoEntity::class, TopicEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class EchoDatabase:RoomDatabase(){

    abstract fun getDao(): EchoDao
}