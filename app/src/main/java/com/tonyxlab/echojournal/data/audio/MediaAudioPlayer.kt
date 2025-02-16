package com.tonyxlab.echojournal.data.audio

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.tonyxlab.echojournal.domain.audio.AudioPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class MediaAudioPlayer @Inject constructor(
    @ApplicationContext private val context: Context
) : AudioPlayer {


    private var filePath = ""
    private var player: MediaPlayer? = null
    private var onCompleteListener: (() -> Unit)? = null

    private val _currentPositionFlow = MutableStateFlow(0)
    override val currentPositionFlow: StateFlow<Int>
        get() = _currentPositionFlow.asStateFlow()


    private var updateJob: Job? = null
    private var isCurrentlyPlaying: Boolean = false

    override fun initializeFile(filePath: String) {

        _currentPositionFlow.value = 0
        this@MediaAudioPlayer.filePath = filePath
        createPlayer()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun play() {

        player ?: createPlayer()

        player?.start()
        player?.metrics
        startUpdatingCurrentPosition()
        isCurrentlyPlaying = true

    }


    override fun pause() {
        checkPlayerIsInitialized()
        player?.pause()
        stopUpdatingCurrentPosition()

    }

    override fun resume() {
        checkPlayerIsInitialized()
        player?.start()
        startUpdatingCurrentPosition()
    }
    override fun stop() {
        checkPlayerIsInitialized()
        stopUpdatingCurrentPosition()
        _currentPositionFlow.value = 0

        player?.stop()
        player?.release()
        player = null
        isCurrentlyPlaying = false
    }

    override fun setOnCompletionListener(listener: () -> Unit) {
        onCompleteListener = listener
    }

    override fun getDuration(): Int {
        checkPlayerIsInitialized()
        return player?.duration ?: 0
    }

    override fun isPlaying(): Boolean {
        return player?.isPlaying  ?: isCurrentlyPlaying
    }

    private fun createPlayer() {

        val medialPlayer = MediaPlayer.create(context, filePath.toUri())
            ?: throw IllegalStateException("Failed to create MediaPlayer, Invalid file path $filePath")

        medialPlayer.apply {

            player = this
            setOnCompletionListener { onCompleteListener?.invoke() }

        }
    }

    private fun checkPlayerIsInitialized() {

        player ?: throw IllegalStateException(
            "Media Player is not initialized, Call 'initializeFile()' first."
        )
    }

    private fun startUpdatingCurrentPosition(){

        checkPlayerIsInitialized()
        stopUpdatingCurrentPosition()

        updateJob = CoroutineScope(Dispatchers.IO).launch {

            while (player?.isPlaying == true){
                _currentPositionFlow.value = player?.currentPosition ?:0
                delay(10L)
            }
        }

    }

    private fun stopUpdatingCurrentPosition(){

        updateJob?.cancel()
        updateJob = null
    }
}