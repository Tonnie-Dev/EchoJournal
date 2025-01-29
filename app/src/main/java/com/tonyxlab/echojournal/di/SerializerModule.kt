package com.tonyxlab.echojournal.di

import com.tonyxlab.echojournal.data.jsonimpl.JsonSerializerImpl
import com.tonyxlab.echojournal.domain.json.JsonSerializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SerializerModule {

    @Binds
    abstract fun bindJsonSerializer(jsonSerializerImpl: JsonSerializerImpl): JsonSerializer

}
