package com.tonyxlab.echojournal.presentation.editor

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.tonyxlab.echojournal.domain.audio.AudioPlayer
import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.domain.usecases.CreateEchoUseCase
import com.tonyxlab.echojournal.domain.usecases.GetEchoByIdUseCase
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class EditorViewModel @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val player: AudioPlayer,
    private val getEchoByIdUseCase: GetEchoByIdUseCase,
    private val updateEchoUseCase: UpdateEchoUseCase,
    private val createEchoUseCase: CreateEchoUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var echo: Echo? = null

    init {

        val id = savedStateHandle.toRoute<SaveScreenObject>().id

        readEchoInfo(id = id)
    }

    private val _editorState = MutableStateFlow(EditorState())
    val editorState = _editorState.asStateFlow()

    var seekFieldValue = MutableStateFlow(
        TextFieldValue(
            value = editorState.value.seekValue,
            onValueChange = this::setSeek

        )
    )

    var titleFieldValue = MutableStateFlow(
        TextFieldValue(
            value = _editorState.value.title,
            onValueChange = this::setTitle

        )
    )
        private set

    var topicFieldValue = MutableStateFlow(
        TextFieldValue(
            value = _editorState.value.topic,
            onValueChange = this::setTopic

        )
    )
        private set
    var descriptionFieldValue = MutableStateFlow(
        TextFieldValue(
            value = _editorState.value.description,
            onValueChange = this::setDescription

        )
    )
        private set


    private fun readEchoInfo(id: String?) {

        id ?: return

        viewModelScope.launch {

            when (val result = getEchoByIdUseCase(id)) {


                is Resource.Success -> {

                    echo = result.data

                    with(result.data) {

                        this@EditorViewModel._editorState.update {
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
            mood = _editorState.value.mood,
            topics = listOf(),
            uri = _editorState.value.recordingUri
        )


        viewModelScope.launch {


            val result = if (this@EditorViewModel != null) {

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


    fun showMoodSelectionSheet() {

        _editorState.update { it.copy(isShowMoodSelectionSheet = true) }

    }

    fun dismissMoodSelectionModalSheet() {


        _editorState.update { it.copy(isShowMoodSelectionSheet = false) }
    }

    fun setSeek(value: Float) {}

    fun play(uri: Uri) {


        _editorState.update { it.copy(isPlaying = true) }
    }


    fun stop() {

        _editorState.update { it.copy(isPlaying = false) }

    }


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

        _editorState.update { it.copy(mood = value) }

    }

    fun confirmMoodSelection() {

        setMood(_editorState.value.mood)

        _editorState.update { it.copy(isShowMoodTitleIcon = true) }
    }

    fun canSave(): Boolean {

        return _editorState.value.mood != Mood.Other && titleFieldValue.value.value.isNotBlank()


    }
}
