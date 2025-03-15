package com.tonyxlab.echojournal.di

import android.content.Context
import androidx.room.Room
import com.tonyxlab.echojournal.data.local.converters.Converters
import com.tonyxlab.echojournal.data.local.dao.EchoDao
import com.tonyxlab.echojournal.data.local.database.EchoDatabase
import com.tonyxlab.echojournal.domain.json.JsonSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private val DATABASE_NAME = "echo_database"


    @Provides
    fun provideEchoDatabase(
        @ApplicationContext context: Context,
        jsonSerializer: JsonSerializer
    ): EchoDatabase {

        return Room.databaseBuilder(
                context = context,
                klass = EchoDatabase::class.java,
                name = DATABASE_NAME
        )
                .addTypeConverter(Converters(serializer = jsonSerializer))
                .fallbackToDestructiveMigration(false)
                .build()
    }

    @Provides
    fun provideDao(database: EchoDatabase): EchoDao {

        return database.getDao()
    }
}