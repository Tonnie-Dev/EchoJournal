package com.tonyxlab.echojournal.di

import com.tonyxlab.echojournal.utils.AppCoroutineDispatchers
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers


@Module
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
