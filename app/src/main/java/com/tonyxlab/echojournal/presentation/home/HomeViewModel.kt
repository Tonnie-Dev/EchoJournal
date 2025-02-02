package com.tonyxlab.echojournal.presentation.home

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonyxlab.echojournal.domain.audio.AudioPlayer
import com.tonyxlab.echojournal.domain.audio.AudioRecorder
import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.domain.usecases.GetEchoesUseCase
import com.tonyxlab.echojournal.utils.TextFieldValue
import com.tonyxlab.echojournal.utils.fromLocalDateTimeToUtcTimeStamp
import com.tonyxlab.echojournal.utils.now
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDateTime
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val recorder: AudioRecorder,
    private val player: AudioPlayer,
    getEchoesUseCase: GetEchoesUseCase,


) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeUiState = _homeState.asStateFlow()

    init {


        getEchoesUseCase().onEach {echoes ->

          _homeState.update { it.copy(echoes = echoes) }

        }.launchIn(viewModelScope)

    }




    var seekFieldValue = MutableStateFlow(
        TextFieldValue(
            value = _homeState.value.seekValue,
            onValueChange = this::setSeek

        )
    )
        private set

    fun createEcho() {

        _homeState.update { it.copy(isRecordingActivated = true) }
    }


    fun play(uri: Uri) {


        _homeState.update { it.copy(isPlaying = true) }
    }


    fun stop() {

        _homeState.update { it.copy(isPlaying = false) }

    }

    fun dismissRecordingModalSheet() {

        _homeState.update { it.copy(isRecordingActivated = false) }
    }

    fun startRecording() {

        val file = File(
            context.filesDir,
            "recording${LocalDateTime.now().fromLocalDateTimeToUtcTimeStamp()}"
        )
            .also {
                recorder.start(it)

            }

        val fileUri = Uri.fromFile(file)
      //  _homeState.update { it.copy(recordingUri = fileUri) }
    }

    fun stopRecording() {

        recorder.stop()
    }

    fun pauseRecording()  {


    }

    fun setSeek(value: Float) {}

}

