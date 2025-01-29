package com.tonyxlab.echojournal.presentation.home

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.tonyxlab.echojournal.domain.audio.AudioPlayer
import com.tonyxlab.echojournal.domain.audio.AudioRecorder
import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.domain.usecases.GetEchoByIdUseCase
import com.tonyxlab.echojournal.presentation.navigation.SaveScreenObject
import com.tonyxlab.echojournal.utils.Resource
import com.tonyxlab.echojournal.utils.TextFieldValue
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val recorder: AudioRecorder,
    private val player: AudioPlayer,
    private val getEchoByIdUseCase: GetEchoByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var echo: Echo? = null

    init {

        val echoId = savedStateHandle.toRoute<SaveScreenObject>().id
        readEchoInfo(echoId = echoId)

    }


    private val _echoes = MutableStateFlow<List<Echo>>(emptyList())
    val echoes = _echoes.asStateFlow()


    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()


    var seekFieldValue = MutableStateFlow(
        TextFieldValue(
            value = _uiState.value.seekValue,
            onValueChange = this::onSeek

        )
    )
        private set


    private fun readEchoInfo(echoId: String?) {

        echoId ?: return

        viewModelScope.launch {

            when (val result = getEchoByIdUseCase(echoId)) {


                is Resource.Success -> {

                    echo = result.data
                }

                is Resource.Error -> Unit
            }
        }


    }

    fun onCreateEcho() {

        _uiState.update { it.copy(isRecordingActivated = true) }
    }

    fun dismissRecordingModalSheet() {


        _uiState.update { it.copy(isRecordingActivated = false) }
    }

    fun onSeek(value: Float) {}

    fun play(uri: Uri) {

        _uiState.update { it.copy(isPlaying = true) }
    }


    fun stop() {

        _uiState.update { it.copy(isPlaying = false) }

    }


    fun startRecording() {


        /*val file1 = File(context.cacheDir, "recording1").also {
            recorder.start(it)

        }*/
        val file2 = File(context.filesDir, "recording1").also {
            recorder.start(it)

        }
    }

    fun stopRecording() {

        recorder.stop()
    }

    fun pauseRecording() {}


}
