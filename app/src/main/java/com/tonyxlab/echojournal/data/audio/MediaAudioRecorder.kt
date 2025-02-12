package com.tonyxlab.echojournal.data.audio

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import com.tonyxlab.echojournal.domain.audio.AudioRecorder
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class MediaAudioRecorder @Inject constructor(@ApplicationContext private val context: Context) :
    AudioRecorder {

    private var recorder: MediaRecorder? = null

    private fun createRecorder(): MediaRecorder {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            MediaRecorder(context)
        } else {

            MediaRecorder()
        }

    }


    override fun start(outputFile: File) {
        createRecorder().apply {

            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(FileOutputStream(outputFile).fd)

            prepare()
            start()

            recorder = this

        }
    }

    override fun stop() {

        recorder?.stop()
        recorder?.reset()
        recorder = null
    }
}