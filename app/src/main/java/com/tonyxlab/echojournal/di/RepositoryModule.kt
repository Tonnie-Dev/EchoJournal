package com.tonyxlab.echojournal.di

import com.tonyxlab.echojournal.data.repository.EchoRepositoryImpl
import com.tonyxlab.echojournal.domain.repository.EchoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindEchoRepository(echoRepositoryImpl: EchoRepositoryImpl):EchoRepository

}
