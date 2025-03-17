package com.tonyxlab.echojournal.presentation.screens.editor.handling


import androidx.lifecycle.viewModelScope
import com.tonyxlab.echojournal.domain.audio.AudioPlayer
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.domain.model.Topic
import com.tonyxlab.echojournal.domain.model.toMood
import com.tonyxlab.echojournal.domain.repository.SettingsRepository
import com.tonyxlab.echojournal.domain.repository.TopicRepository
import com.tonyxlab.echojournal.presentation.core.base.BaseViewModel
import com.tonyxlab.echojournal.presentation.core.state.PlayerState
import com.tonyxlab.echojournal.utils.formatMillisToTime
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

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
    val settingsRepository: SettingsRepository

) : EditorBaseModel() {
    override val initialState: EditorUiState
        get() = EditorUiState()

    private val searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    private val searchResults: StateFlow<List<Topic>> = searchQuery
        .flatMapLatest { query ->

            if (query.isNotBlank()) {

                flowOf(emptyList())
            } else {
                flow {
                    val foundTopics = topicRepository.searchTopics(query)
                    emit(foundTopics)
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

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

    private fun setUpDefaultSettings() {

        launch {

            // TODO: Check for NoSuchElement Crash for flow.first() function
            val defaultTopicsIds = settingsRepository.getTopics().first()
            val defaultMood = settingsRepository.getMood().first()
            val defaultTopics = topicRepository.getTopicsByIds(defaultTopicsIds)
            updateState {

                it.copy(
                    editorSheetState = currentState.editorSheetState.copy(
                        activeMood = defaultMood.toMood()
                    ),
                    currentTopics = defaultTopics
                )
            }
        }
    }

    private fun subscribeToTopicSearchResults() {
        launch {
            searchResults.collect { topics ->
                updateState {
                    it.copy(
                        foundTopics = topics
                    )
                }
            }
        }
    }

    private fun setUpAudioPlayerListeners() {

        audioPlayer.setOnCompletionListener {
            updatePlayerStateAction(PlayerState.Mode.Stopped)
        }
    }

    private fun observeAudioPlayerCurrentPosition() {

        launch {

            audioPlayer.currentPositionFlow.collect { positionMillis ->

                val currentPositionText = positionMillis.toLong().formatMillisToTime()
                updateState {
                    it.copy(
                        playerState = currentState.playerState.copy(
                            currentPosition = positionMillis,
                            currentPositionText = currentPositionText
                        )
                    )
                }
            }
        }
    }

    private fun toggleSheetState(activeMood: Mood = Mood.Undefined) {

        updateSheetState {
            it.copy(
                isSheetOpen = !it.isSheetOpen,
                activeMood = activeMood
            )
        }
    }

    private fun setCurrentMood(mood: Mood) {
        updateState { it.copy(currentMood = mood) }
        toggleSheetState()
    }

    private fun updateActiveMood(mood: Mood) {
        updateState {
            it.copy(
                editorSheetState = currentState.editorSheetState.copy(
                    activeMood = mood
                )
            )
        }
    }

    fun updateTopic(topic: String) {

        updateState { it.copy(topicValue = topic) }
        searchQuery.value = topic
    }

    private fun updateCurrentTopics(newTopic: Topic) {
        updateState {
            it.copy(
                currentTopics = currentState.currentTopics.plus(newTopic),
                topicValue = ""
            )
        }
    }

    private fun addNewTopic() {
        // TODO: Revisit this function
        val newTopic = Topic(name = currentState.topicValue)
        updateCurrentTopics(newTopic)
        launch {
            topicRepository.insertTopic(newTopic)
        }
    }

    private fun playAudio(){
        updatePlayerStateAction(PlayerState.Mode.Playing)
        audioPlayer.play()
    }

    private fun pauseAudio() {

        updatePlayerStateAction(PlayerState.Mode.Paused)
        audioPlayer.pause()
    }

    private fun resumeAudio() {
       updatePlayerStateAction(PlayerState.Mode.Resumed)
        audioPlayer.resume()
    }



    private fun updateSheetState(update: (EditorUiState.EditorSheetState) -> EditorUiState.EditorSheetState) {

        updateState { it.copy(editorSheetState = update(it.editorSheetState)) }

    }

    fun updatePlayerStateAction(mode: PlayerState.Mode) {
        val updatedPlayerState = currentState.playerState.copy(mode = mode)
        updateState { it.copy(playerState = updatedPlayerState) }
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
