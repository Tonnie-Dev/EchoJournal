package com.tonyxlab.echojournal.domain.audio

import java.io.File

interface AudioRecorder{

    fun start()
    fun pause()
    fun resume()
    fun stop(saveFile:Boolean):String

}