package com.tonyxlab.echojournal.presentation.screens.editor.handling


import androidx.lifecycle.viewModelScope
import com.tonyxlab.echojournal.domain.audio.AudioPlayer
import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.domain.model.Topic
import com.tonyxlab.echojournal.domain.model.toMood
import com.tonyxlab.echojournal.domain.repository.EchoRepository
import com.tonyxlab.echojournal.domain.repository.SettingsRepository
import com.tonyxlab.echojournal.domain.repository.TopicRepository
import com.tonyxlab.echojournal.presentation.core.base.BaseViewModel
import com.tonyxlab.echojournal.presentation.core.state.PlayerState
import com.tonyxlab.echojournal.presentation.screens.editor.handling.EditorUiEvent.*
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
import java.io.File

typealias EditorBaseModel = BaseViewModel<EditorUiState, EditorUiEvent, EditorActionEvent>

@HiltViewModel(assistedFactory = EditorViewModel.EditorViewModelFactory::class)
class EditorViewModel @AssistedInject constructor(
    @Assisted("id")
    private val id: Long,
    @Assisted("audioFilePath")
    private val audioFilePath: String,
    private val echoRepository: EchoRepository,
    private val topicRepository: TopicRepository,
    private val audioPlayer: AudioPlayer,
    val settingsRepository: SettingsRepository

) : EditorBaseModel() {
    override val initialState: EditorUiState
        get() = EditorUiState()

    // Original Flow
    private val searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    private val searchResults: StateFlow<List<Topic>> = searchQuery

        .flatMapLatest { query: String ->

            if (query.isBlank()) {

                flowOf(emptyList())
            } else {
                flow {
                    val foundTopics = topicRepository.matchTopics(query)
                    emit(foundTopics)
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    override fun onEvent(event: EditorUiEvent) {

        when (event) {

            BottomSheetClosed -> toggleSheetState()
            is BottomSheetOpened -> toggleSheetState(event.mood)
            is SheetConfirmClicked -> setCurrentMood(event.mood)

            is MoodSelected -> updateActiveMood(event.mood)

            is TitleValueChanged -> updateState { it.copy(titleValue = event.titleValue) }

            is DescriptionValueChanged -> updateState {
                it.copy(descriptionValue = event.descriptionValue)
            }

            is TopicValueChanged -> updateTopic(event.topicValue)

            is TagClearClicked -> updateState {
                it.copy(currentTopics = currentState.currentTopics.minus(event.topic))
            }

            is TopicSelected -> updateCurrentTopics(event.topic)

            CreateTopicClicked -> addNewTopic()

            PlayClicked -> playAudio()
            PauseClicked -> pauseAudio()
            ResumeClicked -> resumeAudio()

            is SaveButtonClicked -> saveEntry(event.outDir)

            ExitDialogToggled -> flipExitDialogState()

            ExitDialogConfirmClicked -> {
                flipExitDialogState()
                audioPlayer.stop()
                sendActionEvent(EditorActionEvent.NavigateBack)
            }


        }
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

    private fun playAudio() {
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

    private fun saveEntry(outputDir: File) {
        val newAudioFilePath = renameFile(outputDir, audioFilePath, "audio")
        val topics = currentState.currentTopics.map { it.name }
        val newEcho = Echo(
            title = currentState.titleValue,
            mood = currentState.currentMood,
            audioFilePath = newAudioFilePath,
            audioDuration = currentState.playerState.duration,
            description = currentState.descriptionValue,
            topics = topics
        )

        launch {
            echoRepository.upsertEcho(newEcho)
            sendActionEvent(EditorActionEvent.NavigateBack)
        }
    }

    private fun flipExitDialogState() {

        updateState { it.copy(showExitDialog = !currentState.showExitDialog) }
    }

    private fun renameFile(outputDir: File, filePath: String, newValue: String): String {

        val file = File(filePath)
        val newFileName = file.name.replace("temp", newValue)
        val newFile = File(outputDir, newFileName)
        val isRenamed = file.renameTo(newFile)

        return if (isRenamed)
            newFile.absolutePath
        else
            throw IllegalStateException("Failed to rename ${file.name}.")

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
