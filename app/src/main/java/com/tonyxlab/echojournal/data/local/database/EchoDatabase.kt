package com.tonyxlab.echojournal.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tonyxlab.echojournal.data.local.converters.InstantConverter
import com.tonyxlab.echojournal.data.local.converters.MoodConverter
import com.tonyxlab.echojournal.data.local.converters.TopicsConverter
import com.tonyxlab.echojournal.data.local.dao.EchoDao
import com.tonyxlab.echojournal.data.local.entity.EchoEntity
import com.tonyxlab.echojournal.data.local.entity.TopicEntity

@Database(entities = [EchoEntity::class], version = 1, exportSchema = false)
@TypeConverters(InstantConverter::class, MoodConverter::class, TopicsConverter::classj)
abstract class EchoDatabase: RoomDatabase(){

    abstract fun getDao(): EchoDao
}