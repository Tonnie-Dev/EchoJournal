package com.tonyxlab.echojournal.di

import com.tonyxlab.echojournal.data.repository.EchoRepositoryImpl
import com.tonyxlab.echojournal.data.repository.SettingsRepositoryImpl
import com.tonyxlab.echojournal.data.repository.TopicRepositoryImpl
import com.tonyxlab.echojournal.domain.repository.EchoRepository
import com.tonyxlab.echojournal.domain.repository.SettingsRepository
import com.tonyxlab.echojournal.domain.repository.TopicRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindTopicRepository(topicRepositoryImpl: TopicRepositoryImpl): TopicRepository

    @Binds
    abstract fun bindEchoRepository(echoRepositoryImpl: EchoRepositoryImpl): EchoRepository

    @Binds
    abstract fun bindSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository
}
