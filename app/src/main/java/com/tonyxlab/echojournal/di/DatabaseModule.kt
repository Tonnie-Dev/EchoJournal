package com.tonyxlab.echojournal.di

import android.content.Context
import androidx.room.Room
import com.tonyxlab.echojournal.data.local.converters.Converters
import com.tonyxlab.echojournal.data.local.dao.EchoDao
import com.tonyxlab.echojournal.data.local.dao.TopicsDao
import com.tonyxlab.echojournal.data.local.database.EchoDatabase
import com.tonyxlab.echojournal.data.local.database.TopicsDatabase
import com.tonyxlab.echojournal.domain.json.JsonSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    const val ECHO_DB_NAME = "echo_database"
    const val TOPIC_DB_NAME = "topic_database"

    @Provides
    fun provideEchoDatabase(
        @ApplicationContext context: Context,
        jsonSerializer: JsonSerializer,
    ): EchoDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = EchoDatabase::class.java,
            name = ECHO_DB_NAME,
        )
            .addTypeConverter(Converters(jsonSerializer = jsonSerializer))
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    fun provideDao(database: EchoDatabase): EchoDao = database.getEchoDao()

    @Provides
    fun provideTopicsDatabase(
        @ApplicationContext context: Context,
    ): TopicsDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = TopicsDatabase::class.java,
            name = TOPIC_DB_NAME,
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    fun provideTopicsDao(database: TopicsDatabase): TopicsDao = database.getTopicsDao()
}