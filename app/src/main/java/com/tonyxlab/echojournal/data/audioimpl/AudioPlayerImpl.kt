package com.tonyxlab.echojournal.data.audioimpl

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import com.tonyxlab.echojournal.domain.audio.AudioPlayer
import java.io.File

class AudioPlayerImpl(private val context: Context) : AudioPlayer {

    private var player: MediaPlayer? = null

    override fun play(file: File) {
        MediaPlayer.create(context, file.toUri()).apply {

            player = this
            start()
        }

    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}