package com.tonyxlab.echojournal.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tonyxlab.echojournal.data.local.converters.Converters
import com.tonyxlab.echojournal.data.local.dao.EchoDao
import com.tonyxlab.echojournal.data.local.entity.EchoEntity
import com.tonyxlab.echojournal.data.local.entity.TopicEntity

@Database(entities = [EchoEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class EchoDatabase: RoomDatabase(){

    abstract fun getDao(): EchoDao
}