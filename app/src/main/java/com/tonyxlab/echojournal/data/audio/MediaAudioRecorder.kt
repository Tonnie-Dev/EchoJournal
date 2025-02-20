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
    private var audioFile: File? = null

   private val outputDir = context.filesDir

    private var isCurrentlyRecording: Boolean = false
    private var isCurrentlyPaused: Boolean = false

    private fun createRecorder(): MediaRecorder {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            MediaRecorder(context)
        } else MediaRecorder()

    }


    override fun start() {

        val audioFileNme = "temp_${System.currentTimeMillis()}.mp3"
        audioFile = File(outputDir, audioFileNme)
        createRecorder().apply {

            setAudioSource(MediaRecorder.AudioSource.MIC)
            setAudioEncodingBitRate(128_000)
            setAudioSamplingRate(44_100)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(audioFile?.absolutePath)

            prepare()
            start()

            recorder = this

        }
        isCurrentlyRecording = true
        isCurrentlyPaused = false
    }

    override fun pause() {
        recorder?.pause()
        isCurrentlyPaused = true
    }

    override fun resume() {

        // TODO: Check if the thrown IllegalStateException is handled
        recorder?.resume() ?: checkRecorderIsInitialized()
        isCurrentlyPaused = false
    }

    override fun stop(saveFile: Boolean): String {

        recorder?.apply {
            stop()
            reset()
            release()
        } ?: checkRecorderIsInitialized()

        recorder = null
        isCurrentlyRecording = false

        audioFile?.let { file ->

            return if (saveFile.not()) {
                file.delete()
                ""
            } else {
                file.absolutePath
            }
        }
            ?: throw IllegalStateException("Audio file was not created, Ensure 'start()' was called before 'stop()'")
    }

    private fun checkRecorderIsInitialized() {

        recorder ?: throw IllegalStateException("Recorder is not initialized, call 'start()' first")
    }
}