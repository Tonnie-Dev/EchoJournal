package com.tonyxlab.echojournal.domain.audio

interface AudioRecorder {

    fun start()

    fun pause()

    fun resume()

    fun stop(saveFile: Boolean): String
}