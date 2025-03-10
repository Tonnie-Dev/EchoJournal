package com.tonyxlab.echojournal.presentation.screens.home

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
import com.tonyxlab.echojournal.presentation.core.state.PlayerState.Mode
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeActionEvent
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent.ActionButtonStartRecording
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent.ActionButtonStopRecording
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent.ActivateMoodFilter
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent.ActivateTopicFilter
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent.CancelMoodFilter
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent.CancelTopicFilter
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent.OpenPermissionDialog
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent.PausePlay
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent.PauseRecording
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent.ResumePlay
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent.ResumeRecording
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent.SelectMoodItem
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent.SelectTopicItem
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent.StartPlay
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent.StartRecording
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent.StopRecording
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiState
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiState.EchoHolderState
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiState.FilterState
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiState.RecordingSheetState
import com.tonyxlab.echojournal.utils.AppCoroutineDispatchers
import com.tonyxlab.echojournal.utils.StopWatch
import com.tonyxlab.echojournal.utils.formatMillisToTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


private typealias HomeBaseViewModel = BaseViewModel<HomeUiState, HomeUiEvent, HomeActionEvent>

@HiltViewModel
class HomeViewModel @Inject constructor(
    appCoroutineDispatchers: AppCoroutineDispatchers,
    private val audioRecorder: AudioRecorder,
    private val audioPlayer: AudioPlayer,
    private val getEchoesUseCase: GetEchoesUseCase,
) : HomeBaseViewModel() {


    override val initialState: HomeUiState
        get() = HomeUiState()

    override fun onEvent(event: HomeUiEvent) {

        Timber.i("onEvent Called")

        when (event) {

            ActivateMoodFilter -> activateMoodFilter()
            is SelectMoodItem -> selectMoodItem(event.mood.name)
            CancelMoodFilter -> clearMoodFilterItem()

            ActivateTopicFilter -> activateTopicFilter()
            is SelectTopicItem -> selectTopicItem(event.topic)
            CancelTopicFilter -> clearTopicFilterItem()

            is OpenPermissionDialog -> updateState { it.copy(isPermissionDialogOpen = event.isOpen) }

            StartRecording -> {
                flipRecordingState()
                startRecording()
            }

            PauseRecording -> pauseRecording()
            ResumeRecording -> resumeRecording()

            is StopRecording -> {
                flipRecordingState()
                stopRecording(isSaveFile = event.saveFile)
            }

            ActionButtonStartRecording -> startRecording()

            is ActionButtonStopRecording -> stopRecording(isSaveFile = event.saveFile)

            is StartPlay -> startPlay(echoId = event.echoId)
            is PausePlay -> pausePlay(echoId = event.echoId)
            is ResumePlay -> resumePlay(echoId = event.echoId)
        }
    }

    private val stopWatch = StopWatch(appCoroutineDispatchers)
    private var stopWatchJob: Job? = null

    private val selectedMoodFilters = MutableStateFlow<List<FilterState.FilterItem>>(emptyList())
    private val selectedTopicFilters = MutableStateFlow<List<FilterState.FilterItem>>(emptyList())
    private val filteredEchoes = MutableStateFlow<Map<Long, List<EchoHolderState>>?>(emptyMap())
    private var fetchedEchoes: Map<Long, List<EchoHolderState>> = emptyMap()


    private var playingEchoId = MutableStateFlow<String?>(null)


    init {


        observeEntries()
        observeFilters()
        setUpAudioPlayerListeners()
        observeAudioPlayerCurrentPosition()

    }

    private fun observeEntries() {

        var isFirstLoad = true


        getEchoesUseCase().combine(filteredEchoes) {

                echoes, currentlyFilteredEchoes ->

            val topics = mutableSetOf<String>()

            val sortedEchoes =
                if (currentlyFilteredEchoes != null && currentlyFilteredEchoes.isEmpty()) {
                    fetchedEchoes = groupEchoesByDate(
                        echoes = echoes,
                        topics = topics
                    )

                    fetchedEchoes

                } else currentlyFilteredEchoes ?: emptyMap()
            /* val sortedEchoes = currentlyFilteredEchoes?.ifEmpty {
                 fetchedEchoes = groupEchoesByDate(
                     echoes = echoes,
                     topics = topics
                 )*/
            fetchedEchoes


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


        // Set a listener to handle actions when audio playback completes
        audioPlayer.setOnCompletionListener {

            playingEchoId.value?.let { echoId ->

                updatePlayerStateAction(echoId, PlayerState.Mode.Stopped)
                audioPlayer.stop()
            }



        }
    }

    private fun updatePlayerStateAction(echoId: String, mode: Mode) {
        Timber.i("The EchoId is $echoId")
        Timber.i("L262 Called")
        val echoHolderState = getCurrentEchoHolderState(echoId)
        val updatedPlayerState = echoHolderState.playerState.copy(mode = mode)
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
        Timber.i("My Id is: $id")
        return currentState.echoes.values
            .flatten()
            .firstOrNull {
                Timber.i("The other id is: ${it.echo.id}")
                it.echo.id == id

            }
            ?: throw IllegalStateException("Audio file path not found for entry Id: $id")


    }

    private fun observeAudioPlayerCurrentPosition() {

        launch {

            audioPlayer.currentPositionFlow.collect { positionMillis ->

                val currentPositionText = positionMillis.toLong().formatMillisToTime()

                playingEchoId.value?.let { echoId ->

                    updatePlayerSateCurrentPosition(
                        echoId = echoId,
                        currentPosition = positionMillis,
                        currentPositionText =currentPositionText
                    )
                }


            }
        }

    }

    private fun updatePlayerSateCurrentPosition(
        echoId: String,
        currentPosition: Int,
        currentPositionText: String
    ) {

        Timber.i("L324 Called")
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

    private fun updateTopicFilterItems(
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
            currentState.recordingSheetState.copy(
                isVisible = !currentState.recordingSheetState.isVisible,
                isRecording = true
            )
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
            stopPlay()
            sendActionEvent(HomeActionEvent.NavigateToEditorScreen(Uri.encode(audioFilePath)))
        }
    }


    fun startPlay(echoId: String) {
        Timber.i("506 Called")

        if (audioPlayer.isPlaying()) {
            stopPlay()
            audioPlayer.stop()
        }

        playingEchoId.value = echoId

        updatePlayerStateAction(
            echoId = echoId,
            mode = Mode.Playing
        )

        val audioFilePath = getCurrentEchoHolderState(echoId).echo.uri.toString()
        audioPlayer.initializeFile(audioFilePath)
        audioPlayer.play()

    }

    private fun resumePlay(echoId: String) {
        updatePlayerStateAction(echoId = echoId, mode = Mode.Resumed)
        audioPlayer.resume()
    }

    private fun pausePlay(echoId: String) {
        updatePlayerStateAction(echoId = echoId, mode = Mode.Paused)
        audioPlayer.pause()
    }

    private fun stopPlay() {

        val updatedEchoes = currentState.echoes.mapValues { (_, echoHolderStateList) ->

            echoHolderStateList.map { echoHolderState ->

                if (
                    echoHolderState.playerState.mode == Mode.Playing ||
                    echoHolderState.playerState.mode == Mode.Paused
                ) {

                    val updatedPlayerState = echoHolderState.playerState.copy(
                        mode = Mode.Stopped,
                        currentPosition = 0,
                        currentPositionText = "00:00"

                    )
                    echoHolderState.copy(playerState = updatedPlayerState)
                } else echoHolderState

            }
        }

        updateState { it.copy(echoes = updatedEchoes) }

    }


    /* var seekFieldValue = MutableStateFlow(
         TextFieldValue(
             value = _homeState.value.seekValue,
             onValueChange = this::setSeek

         )
     )
         private set*/

}

