package com.tonyxlab.echojournal.domain.audio

import java.io.File

interface AudioRecorder {

    fun start(outputFile: File)
    fun stop()
}