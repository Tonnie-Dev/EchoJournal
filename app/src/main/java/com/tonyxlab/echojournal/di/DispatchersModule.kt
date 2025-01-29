package com.tonyxlab.echojournal.di

import com.tonyxlab.echojournal.utils.AppCoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers


@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    fun provideCoroutineDispatchers(): AppCoroutineDispatchers {

        return AppCoroutineDispatchers(
                main = Dispatchers.Main,
                io = Dispatchers.IO,
                computation = Dispatchers.Default
        )
    }
}
