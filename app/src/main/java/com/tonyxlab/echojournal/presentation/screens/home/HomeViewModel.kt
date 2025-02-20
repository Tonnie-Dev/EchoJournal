package com.tonyxlab.echojournal.presentation.screens.home

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.tonyxlab.echojournal.domain.audio.AudioPlayer
import com.tonyxlab.echojournal.domain.audio.AudioRecorder
import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.domain.model.toMood
import com.tonyxlab.echojournal.domain.usecases.GetEchoesUseCase
import com.tonyxlab.echojournal.presentation.core.base.BaseViewModel
import com.tonyxlab.echojournal.presentation.core.state.PlayerState
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeActionEvent
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent.*
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiState
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiState.*
import com.tonyxlab.echojournal.utils.AppCoroutineDispatchers
import com.tonyxlab.echojournal.utils.StopWatch
import com.tonyxlab.echojournal.utils.TextFieldValue
import com.tonyxlab.echojournal.utils.formatMillisToTime
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


private typealias HomeBaseViewModel = BaseViewModel<HomeUiState, HomeUiEvent, HomeActionEvent>

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val audioRecorder: AudioRecorder,
    private val audioPlayer: AudioPlayer,
    private val getEchoesUseCase: GetEchoesUseCase,
) : HomeBaseViewModel() {


    override val initialState: HomeUiState
        get() = HomeUiState()

    override fun onEvent(event: HomeUiEvent) {

        when (event) {

            ActivateMoodFilter -> activateMoodFilter()
            is SelectMoodItem -> selectMoodItem(event.mood.name)
            ClearMoodItem -> clearMoodFilterItem()

            ActivateTopicFilter -> activateTopicFilter()
            is SelectTopicItem -> selectTopicItem(event.topic)
            ClearTopicItem -> clearTopicFilterItem()

            is OpenPermissionDialog -> updateState { it.copy(isPermissionDialogOpen = event.isOpen) }

            StartRecording -> {
                startRecording()
            }
        }
    }

    private val stopWatch = StopWatch(appCoroutineDispatchers)
    private var stopWatchJob: Job? = null

    private val selectedMoodFilters = MutableStateFlow<List<FilterState.FilterItem>>(emptyList())
    private val selectedTopicFilters = MutableStateFlow<List<FilterState.FilterItem>>(emptyList())
    private val filteredEchoes = MutableStateFlow<Map<Long, List<EchoHolderState>>?>(emptyMap())
    private var fetchedEchoes: Map<Long, List<EchoHolderState>> = emptyMap()


    private var playingEchoId = MutableStateFlow("")


    init {


        observeEntries()
        observeFilters()
        setUpAudioPlayerListeners()
        observeAudionPLayerCurrentPosition()

    }

    private fun observeEntries() {

        var isFirstLoad = true


        getEchoesUseCase().combine(filteredEchoes) {

                echoes, currentlyFilteredEchoes ->

            val topics = mutableSetOf<String>()

            val sortedEchoes = currentlyFilteredEchoes.ifEmpty {
                fetchedEchoes = groupEchoesByDate(
                    echoes = echoes,
                    topics = topics
                )
                fetchedEchoes

            }
            val updatedTopicFilterItems = addNewTopicFilterItems(topics = topics.toList())

            updateState {
                it.copy(
                    echoes = sortedEchoes,
                    filterState = currentState.filterState.copy(topicFilterItems = updatedTopicFilterItems)
                )
            }

            if (isFirstLoad) {
                sendActionEvent(HomeActionEvent.DataLoaded)
                isFirstLoad = false
            }

        }.launchIn(viewModelScope)


    }


    private fun observeFilters() {

        combine(selectedMoodFilters, selectedTopicFilters) { moodFilters, topicFilters ->


            val moodTypes = moodFilters.map { it.title.toMood() }
            val topicTitles = topicFilters.map { it.title }
            val isFilterActive = moodFilters.isNotEmpty() || topicFilters.isNotEmpty()

            filteredEchoes.value = if (isFilterActive) {
                getFilteredEchoes(fetchedEchoes, moodTypes, topicTitles)

            } else emptyMap()

        }.launchIn(viewModelScope)
    }

    private fun groupEchoesByDate(
        echoes: List<Echo>,
        topics: MutableSet<String>
    ): Map<Long, List<EchoHolderState>> {


        return echoes.groupBy { echo ->

            echo.topics.forEach { topic ->

                if (!topics.contains(topic)) topics.add(topic)
            }

            echo.timestamp
        }.mapValues { (_, echoesList) ->

            echoesList.map { EchoHolderState(echo = it) }
        }


    }


    private fun getFilteredEchoes(
        echoes: Map<Long, List<EchoHolderState>>,
        moodFilters: List<Mood>,
        topicsFilters: List<String>
    ): Map<Long, List<EchoHolderState>>? {

        return echoes.mapValues { (_, echoesList) ->

            echoesList.filter { echoHolderState ->
                val echo = echoHolderState.echo
                echo.mood in moodFilters || echo.topics.any { it in topicsFilters }
            }


        }.filterValues { it.isNotEmpty() }.ifEmpty { null }


    }

    private fun addNewTopicFilterItems(topics: List<String>): List<FilterState.FilterItem> {


        val currentTopics = currentState.filterState.topicFilterItems.map {
            it.title

        }

        val newTopicItems = currentState.filterState.topicFilterItems.toMutableList()

        topics.forEach { topic ->

            if (!currentTopics.contains(topic)) {
                newTopicItems.add(FilterState.FilterItem(title = topic))
            }
        }

        return newTopicItems
    }


    private fun setUpAudioPlayerListeners() {
        audioPlayer.setOnCompletionListener {

            with(playingEchoId.value) {
                updatePlayerStateAction(this, PlayerState.Action.Initializing)
                audioPlayer.stop()
            }
        }
    }


    private fun updatePlayerStateAction(echoId: String, action: PlayerState.Action) {

        val echoHolderState = getCurrentEchoHolderState(echoId)
        val updatedPlayerState = echoHolderState.playerState.copy(action = action)
        updatePlayerState(echoId = echoId, newPlayerState = updatedPlayerState)

    }

    private fun updatePlayerState(echoId: String, newPlayerState: PlayerState) {

        val updatedEchoes = currentState.echoes.mapValues { (_, echoesList) ->

            echoesList.map { echoHolderState ->

                if (echoHolderState.echo.id == echoId) {

                    echoHolderState.copy(playerState = newPlayerState)
                } else echoHolderState


            }
        }

        updateState { it.copy(echoes = updatedEchoes) }
    }

    private fun getCurrentEchoHolderState(id: String): EchoHolderState {

        return currentState.echoes.values
            .flatten()
            .firstOrNull { it.echo.id == id }
            ?: throw IllegalStateException("Audio file path not found for entry Id $id ")


    }

    private fun observeAudionPLayerCurrentPosition() {

        launch {

            audioPlayer.currentPositionFlow.collect { positionMillis ->

                val currentPosition = positionMillis.toLong().formatMillisToTime()
                updatePlayerSateCurrentPosition(
                    echoId = playingEchoId.value,
                    currentPosition = positionMillis,
                    currentPositionText = currentPosition
                )

            }
        }

    }

    private fun updatePlayerSateCurrentPosition(
        echoId: String,
        currentPosition: Int,
        currentPositionText: String
    ) {

        val echoHolderState = getCurrentEchoHolderState(id = echoId)
        val updatedPlayerState = echoHolderState.playerState.copy(

            currentPosition = currentPosition,
            currentPositionText = currentPositionText
        )
        updatePlayerState(echoId, updatedPlayerState)
    }

    private fun updateMoodFilterItems(
        updatedMoodItems: List<FilterState.FilterItem>,
        moodFilterSelectionOpen: Boolean = true
    ) {
        updateState {

            it.copy(
                filterState = currentState.filterState.copy(

                    moodFilterItems = updatedMoodItems,
                    isMoodFilterOpen = moodFilterSelectionOpen
                )
            )
        }
    }

    private fun activateMoodFilter() {

        val updatedFilterState = currentState.filterState.copy(
            isMoodFilterOpen = !currentState.filterState.isMoodFilterOpen,
            isTopicFilterOpen = false
        )

        updateState { it.copy(filterState = updatedFilterState) }
    }

    private fun selectMoodItem(title: String) {

        val updatedMoodItems = currentState.filterState.moodFilterItems.map {

            if (it.title == title) it.copy(isChecked = !it.isChecked) else it
        }
        selectedMoodFilters.value = updatedMoodItems.filter { it.isChecked }
        updateMoodFilterItems(updatedMoodItems = updatedMoodItems)
    }


    private fun clearMoodFilterItem() {
        selectedMoodFilters.value = emptyList()
        val updatedMoodItems = currentState.filterState.moodFilterItems.map {
            if (it.isChecked) it.copy(isChecked = false) else it
        }

        updateMoodFilterItems(updatedMoodItems = updatedMoodItems, moodFilterSelectionOpen = false)
    }

    fun updateTopicFilterItems(
        updatedItems: List<FilterState.FilterItem>,
        topicFilterSelectionOpen: Boolean = true
    ) {

        updateState {

            it.copy(
                filterState = currentState.filterState.copy(
                    topicFilterItems = updatedItems,
                    isTopicFilterOpen = topicFilterSelectionOpen
                )
            )
        }


    }

    private fun activateTopicFilter() {

        val updatedFilterState = currentState.filterState.copy(
            isTopicFilterOpen = !currentState.filterState.isTopicFilterOpen,
            isMoodFilterOpen = false
        )

        updateState { it.copy(filterState = updatedFilterState) }
    }


    private fun selectTopicItem(title: String) {


        val updatedTopicItems = currentState.filterState.topicFilterItems.map {

            if (it.title == title) it.copy(isChecked = !it.isChecked) else it
        }

        selectedTopicFilters.value = updatedTopicItems

        updateTopicFilterItems(updatedTopicItems)
    }

    private fun clearTopicFilterItem() {

        selectedTopicFilters.value = emptyList()

        val updatedTopicItems = currentState.filterState.topicFilterItems.map {

            if (it.isChecked) it.copy(isChecked = false) else it
        }
    }


    private fun updateRecordingSheetState(updatedRecordingSheetState: RecordingSheetState) {

        updateState { it.copy(recordingSheetState = updatedRecordingSheetState) }
    }

    private fun flipRecordingState() {

        val updatedRecordingSheetState =
            currentState.recordingSheetState.copy(isRecording = !currentState.recordingSheetState.isRecording)
        updateRecordingSheetState(updatedRecordingSheetState)
    }

    private fun startRecording() {

        audioRecorder.start()
        stopWatch.start()

        viewModelScope.launch {

            stopWatch.formattedTime.collect {
                val updatedRecordingSheetState =
                    currentState.recordingSheetState.copy(recordingTime = it)
                updateRecordingSheetState(updatedRecordingSheetState)

            }
        }

    }

    private fun pauseRecording() {

        audioRecorder.pause()
        stopWatch.pause()
        flipRecordingState()

    }

    private fun resumeRecording() {

        audioRecorder.resume()
        stopWatch.start()
        flipRecordingState()
    }

    private fun stopRecording(isSaveFile: Boolean) {

        val audioFilePath = audioRecorder.stop(isSaveFile)
        stopWatch.reset()
        stopWatchJob?.cancel()

        if (isSaveFile) {


        }
    }


    fun playEcho(uri: Uri) {


        if (audioPlayer.isPlaying()) {
            stopPlayingEcho()
        }
    }


    private fun stopPlayingEcho() {

        val updatedEcho = currentState.echoes.mapValues {
            (_, echoHolderStateList) ->

            echoHolderStateList.map { echoHolderState ->

                if (
                    echoHolderState.playerState.action == PlayerState.Action.Playing ||
                    echoHolderState.playerState.action == PlayerState.Action.Paused
                ) {

                    val updatedPlayerState = echoHolderState.playerState.copy(
                        action = PlayerState.Action.Initializing,
                        currentPosition = 0,
                        currentPositionText = "00:00"

                    )
                    echoHolderState.copy(playerState = updatedPlayerState)
                } else echoHolderState


            }
        }

    }

    fun dismissRecordingModalSheet() {

        _homeState.update { it.copy(isRecordingActivated = false) }
    }

    var seekFieldValue = MutableStateFlow(
        TextFieldValue(
            value = _homeState.value.seekValue,
            onValueChange = this::setSeek

        )
    )
        private set

}

