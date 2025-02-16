package com.tonyxlab.echojournal.domain.audio

import kotlinx.coroutines.flow.Flow
import java.io.File


interface AudioPlayer{

    val currentPositionFlow:Flow<Int>

    fun initializeFile(filePath:String)
    fun play ()
    fun pause()
    fun resume()
    fun stop()
    fun setOnCompletionListener(listener:() -> Unit)
    fun getDuration():Int
    fun isPlaying():Boolean

}