package com.tonyxlab.echojournal.di

import com.tonyxlab.echojournal.data.audioimpl.AudioPlayerImpl
import com.tonyxlab.echojournal.data.audioimpl.AudioRecorderImpl
import com.tonyxlab.echojournal.domain.audio.AudioPlayer
import com.tonyxlab.echojournal.domain.audio.AudioRecorder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AudioModule {

    @Binds
    abstract fun bindAudioRecorder(audioRecorder: AudioRecorderImpl): AudioRecorder

    @Binds
    abstract fun bindAudioPlayer(audioPlayer: AudioPlayerImpl): AudioPlayer

}

