package com.tonyxlab.echojournal.di

import com.tonyxlab.echojournal.data.audio.MediaAudioPlayer
import com.tonyxlab.echojournal.data.audio.MediaAudioRecorder
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
    abstract fun bindAudioRecorder(audioRecorder: MediaAudioRecorder): AudioRecorder

    @Binds
    abstract fun bindAudioPlayer(audioPlayer: MediaAudioPlayer): AudioPlayer
}
