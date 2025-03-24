package com.tonyxlab.echojournal.di

import android.content.Context
import androidx.room.Room
import com.tonyxlab.echojournal.data.local.converters.InstantConverter
import com.tonyxlab.echojournal.data.local.converters.MoodConverter
import com.tonyxlab.echojournal.data.local.converters.TopicsConverter
import com.tonyxlab.echojournal.data.local.dao.EchoDao
import com.tonyxlab.echojournal.data.local.dao.TopicsDao
import com.tonyxlab.echojournal.data.local.database.EchoDatabase
import com.tonyxlab.echojournal.data.local.database.TopicsDatabase
import com.tonyxlab.echojournal.domain.json.JsonSerializer
import com.tonyxlab.echojournal.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideEchoDatabase(
        @ApplicationContext context: Context,
        jsonSerializer: JsonSerializer
    ): EchoDatabase {

        return Room.databaseBuilder(
            context = context,
            klass = EchoDatabase::class.java,
            name = Constants.ECHO_DB_NAME
        )
           .addTypeConverter(InstantConverter(jsonSerializer = jsonSerializer))
            .addTypeConverter(TopicsConverter(jsonSerializer = jsonSerializer))
            .addTypeConverter(MoodConverter(jsonSerializer = jsonSerializer))
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun provideDao(database: EchoDatabase): EchoDao  = database.getEchoDao()

    @Provides
    fun provideTopicsDatabase(@ApplicationContext context: Context): TopicsDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = TopicsDatabase::class.java,
            name = Constants.TOPIC_DB_NAME
        ).build()
    }

    @Provides
    fun provideTopicsDao(database: TopicsDatabase): TopicsDao = database.getTopicsDao()

}