package com.tonyxlab.echojournal.domain.audio

import java.io.File
import javax.inject.Inject

interface AudioRecorder{

    fun start(outputFile: File)
    fun stop()
}