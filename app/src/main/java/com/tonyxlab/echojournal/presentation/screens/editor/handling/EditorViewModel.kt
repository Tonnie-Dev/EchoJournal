package com.tonyxlab.echojournal.presentation.screens.editor.handling


import com.tonyxlab.echojournal.domain.audio.AudioPlayer
import com.tonyxlab.echojournal.domain.repository.TopicRepository
import com.tonyxlab.echojournal.presentation.core.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

typealias EditorBaseModel = BaseViewModel<EditorUiState, EditorUiEvent, EditorActionEvent>

@HiltViewModel(assistedFactory = EditorViewModel.EditorViewModelFactory::class)
class EditorViewModel @AssistedInject constructor(
    @Assisted("id")
    private val id: Long,
    @Assisted("audioFilePath")
    private val audioFilePath: String,
    private val echoRepository: TopicRepository,
    private val topicRepository: TopicRepository,
    private val audioPlayer: AudioPlayer,


    ) : EditorBaseModel() {
    override val initialState: EditorUiState
        get() = EditorUiState()

    override fun onEvent(event: EditorUiEvent) {
        TODO("Not yet implemented")
    }

    private fun initializeAudioPlayer() {
        audioPlayer.initializeFile(audioFilePath)

        updateState {
            it.copy(
                playerState = currentState.playerState.copy(
                    duration = audioPlayer.getDuration()
                )
            )
        }

    }

    private fun setUpDefaultSettings(){

        launch {

            val defaultTopics = topicRepository.getTopicsByIds(defaultTopicIds)
        }
    }

    @AssistedFactory
    interface EditorViewModelFactory {

        fun create(
            @Assisted("id")
            id: Long,
            @Assisted("audioFilePath")
            audioFilePath: String
        ): EditorViewModel

    }
}
