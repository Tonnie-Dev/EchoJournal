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
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.domain.usecases.GetEchoByIdUseCase
import com.tonyxlab.echojournal.domain.usecases.GetEchoesUseCase
import com.tonyxlab.echojournal.presentation.navigation.SaveScreenObject
import com.tonyxlab.echojournal.utils.Resource
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
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import java.io.File
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val recorder: AudioRecorder,
    private val player: AudioPlayer,
    private val getEchoByIdUseCase: GetEchoByIdUseCase,
    getEchoesUseCase: GetEchoesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var echo: Echo? = null

    private val _echoes = MutableStateFlow<List<Echo>>(emptyList())
    val echoes = _echoes.asStateFlow()

    init {


        getEchoesUseCase().onEach {

            _echoes.value = it

        }.launchIn(viewModelScope)

        val echoId = savedStateHandle.toRoute<SaveScreenObject>().id
        readEchoInfo(echoId = echoId)

    }


    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    var seekFieldValue = MutableStateFlow(
        TextFieldValue(
            value = _uiState.value.seekValue,
            onValueChange = this::onSeek

        )
    )
        private set


    var titleFieldValue = MutableStateFlow(
        TextFieldValue(
            value = _uiState.value.title,
            onValueChange = this::setTitle

        )
    )
        private set

    var topicFieldValue = MutableStateFlow(
        TextFieldValue(
            value = _uiState.value.topic,
            onValueChange = this::setTopic

        )
    )
        private set
    var descriptionFieldValue = MutableStateFlow(
        TextFieldValue(
            value = _uiState.value.description,
            onValueChange = this::setDescription

        )
    )
        private set


    private fun readEchoInfo(echoId: String?) {

        echoId ?: return

        viewModelScope.launch {

            when (val result = getEchoByIdUseCase(echoId)) {


                is Resource.Success -> {

                    echo = result.data

                    with(result.data) {

                        _uiState.update {
                            it.copy(
                                currentTopics = topics,
                                description = description,
                                title = name,


                                )
                        }
                    }
                }

                is Resource.Error -> Unit
            }
        }


    }

    private fun doSave() {

        val echoItem = Echo(
            id = this.echo?.id ?: UUID.randomUUID().toString(),
            name = titleFieldValue.value.value,
            description = descriptionFieldValue.value.value,
            timestamp = LocalDateTime.now().fromLocalDateTimeToUtcTimeStamp(),
            length = 0,
            mood = Mood.Other,
            topics = listOf(),
            uri = _uiState.value.recordingUri
        )
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


        val file = File(
            context.filesDir,
            "recording${LocalDateTime.now().fromLocalDateTimeToUtcTimeStamp()}"
        )
            .also {
                recorder.start(it)

            }

        val fileUri = Uri.fromFile(file)
        _uiState.update { it.copy(recordingUri = fileUri) }
    }

    fun stopRecording() {

        recorder.stop()
    }

    fun pauseRecording() {}

    private fun setTitle(value: String) {


        /* if (value.isBlank()) {
             // Show snackbar or handle the error
             // Example: _uiState.update { it.copy(showSnackbar = true, snackbarMessage = "Title cannot be blank") }
         } else {
             titleFieldValue.update { it.copy(value = value) }
         }*/
        titleFieldValue.update { it.copy(value = value) }

    }

    private fun setTopic(value: String) {

        topicFieldValue.update { it.copy(value = value) }

    }

    private fun setDescription(value: String) {

        descriptionFieldValue.update { it.copy(value = value) }

    }
}

