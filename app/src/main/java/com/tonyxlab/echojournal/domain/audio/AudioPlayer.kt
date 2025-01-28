package com.tonyxlab.echojournal.domain.audio

import java.io.File


interface AudioPlayer{

    fun play (file: File)
    fun stop()
}