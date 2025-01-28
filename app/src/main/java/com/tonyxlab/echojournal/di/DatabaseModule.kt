package com.tonyxlab.echojournal.di

import android.content.Context
import androidx.room.Room
import com.tonyxlab.echojournal.data.database.EchoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{

    private val DATABASE_NAME = "echo_database"


    @Provides
    fun provideEchoDatabase(@ApplicationContext context: Context):EchoDatabase{


        return Room.databaseBuilder(context,EchoDatabase::class.java, DATABASE_NAME).build()
    }
}