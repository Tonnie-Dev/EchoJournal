package com.tonyxlab.echojournal.domain.recorder

import java.io.File

interface AudioRecorder {

    fun start(outputFile: File)
    fun stop()
}