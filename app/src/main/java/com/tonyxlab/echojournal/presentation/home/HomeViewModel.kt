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
import com.tonyxlab.echojournal.domain.usecases.CreateEchoUseCase
import com.tonyxlab.echojournal.domain.usecases.GetEchoByIdUseCase
import com.tonyxlab.echojournal.domain.usecases.GetEchoesUseCase
import com.tonyxlab.echojournal.domain.usecases.UpdateEchoUseCase
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
import timber.log.Timber
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
    private val updateEchoUseCase: UpdateEchoUseCase,
    private val createEchoUseCase: CreateEchoUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var echo: Echo? = null

    private val _echoes = MutableStateFlow<List<Echo>>(emptyList())
    val echoes = _echoes.asStateFlow()

    init {

        val echoId = savedStateHandle.toRoute<SaveScreenObject>().id

        Timber.i("Id is $echoId")
        readEchoInfo(echoId = echoId)
        getEchoesUseCase().onEach {

            _echoes.value = it

        }.launchIn(viewModelScope)



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
                                title = title,


                                )
                        }

                        setTitle(this.title)
                        setMood(mood)
                       setDescription(description)
                    }
                }

                is Resource.Error -> Unit
            }
        }


    }

    fun doSave() {

        val echoItem = Echo(
            id = this.echo?.id ?: UUID.randomUUID().toString(),
            title = titleFieldValue.value.value,
            description = descriptionFieldValue.value.value,
            timestamp = LocalDateTime.now().fromLocalDateTimeToUtcTimeStamp(),
            length = 0,
            mood = _uiState.value.mood,
            topics = listOf(),
            uri = _uiState.value.recordingUri
        )


        viewModelScope.launch {


            val result = if (this@HomeViewModel.echo != null) {

                updateEchoUseCase(echo = echoItem)
            } else {
                createEchoUseCase(echo = echoItem)
            }

            when (result) {

                is Resource.Success -> {

                    Timber.i("Saved")
                }

                is Resource.Error -> {

                    Timber.i("Saving Failed: ${result.exception.message}")
                }
            }
        }


    }

    fun createEcho() {

        _uiState.update { it.copy(isRecordingActivated = true) }
    }

    fun dismissRecordingModalSheet() {


        _uiState.update { it.copy(isRecordingActivated = false) }
    }

    fun showMoodSelectionSheet() {

        _uiState.update { it.copy(isShowMoodSelectionSheet = true) }

    }

    fun dismissMoodSelectionModalSheet() {


        _uiState.update { it.copy(isShowMoodSelectionSheet = false) }
    }

    fun onSeek(value: Float) {}

    fun play(uri: Uri) {

       

        _uiState.update { it.copy(isPlaying = true) }
    }


    fun stop() {

        _uiState.update { it.copy(isPlaying = false) }

    }


    fun startRecording() {

        Timber.i("Start recording Called")

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

        Timber.i("Stop recording Called")
        recorder.stop()
    }

    fun pauseRecording() {}

    private fun setTitle(value: String) {


        titleFieldValue.update { it.copy(value = value) }

    }

    private fun setTopic(value: String) {

        topicFieldValue.update { it.copy(value = value) }

    }

    private fun setDescription(value: String) {

        descriptionFieldValue.update { it.copy(value = value) }

    }


    fun setMood(value: Mood) {

        _uiState.update { it.copy(mood = value) }

    }

    fun confirmMoodSelection() {

        setMood(uiState.value.mood)

        _uiState.update { it.copy(isShowMoodTitleIcon = true) }
    }

    fun canSave(): Boolean {

        return _uiState.value.mood != Mood.Other && titleFieldValue.value.value.isNotBlank()


    }
}

