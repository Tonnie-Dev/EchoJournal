package com.tonyxlab.echojournal.presentation.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.utils.TextFieldValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

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


    fun startRecording() {}
    fun stopRecording() {}
    fun pauseRecording() {}


}
