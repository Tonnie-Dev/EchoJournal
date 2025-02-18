package com.tonyxlab.echojournal.presentation.screens.home

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonyxlab.echojournal.domain.audio.AudioPlayer
import com.tonyxlab.echojournal.domain.audio.AudioRecorder
import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.domain.usecases.GetEchoesUseCase
import com.tonyxlab.echojournal.presentation.core.base.BaseViewModel
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeActionEvent
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiState
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiState.*
import com.tonyxlab.echojournal.utils.AppCoroutineDispatchers
import com.tonyxlab.echojournal.utils.StopWatch
import com.tonyxlab.echojournal.utils.TextFieldValue
import com.tonyxlab.echojournal.utils.fromLocalDateTimeToUtcTimeStamp
import com.tonyxlab.echojournal.utils.now
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDateTime
import java.io.File
import javax.inject.Inject


private typealias HomeBaseViewModel = BaseViewModel<HomeUiState, HomeUiEvent, HomeActionEvent>

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val recorder: AudioRecorder,
    private val player: AudioPlayer,
    private val getEchoesUseCase: GetEchoesUseCase,
) : HomeBaseViewModel() {


    override val initialState: HomeUiState
        get() = HomeUiState()

    private val stopWatch = StopWatch(appCoroutineDispatchers)
    private var stopWatchJob: Job? = null

    private val selectedMoodFilters = MutableStateFlow<List<FilterState.FilterItem>>(emptyList())
    private val selectedTopicFilters = MutableStateFlow<List<FilterState.FilterItem>>(emptyList())
    private val filteredEchoes = MutableStateFlow<Map<Long, List<EchoHolderState>>>(emptyMap())
    private var fetchedEchoes: Map<Long, List<EchoHolderState>> = emptyMap()


    private var playingEntryId = MutableStateFlow("")


    init {


        observeEntries()
        observeFilters()

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
                    entries = sortedEchoes,
                    filterState = currentState.filterState.copy(topicFilterItems = updatedTopicFilterItems)
                )
            }

            if (isFirstLoad){
                sendActionEvent(HomeActionEvent.DataLoaded)
                isFirstLoad = false
            }

        }.launchIn(viewModelScope)


    }


    private fun observeFilters(){

        combine(selectedMoodFilters, selectedTopicFilters){ moodFilters, topicFilters ->


           val moodTypes = moodFilters.map { it.title }

        }
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

    var seekFieldValue = MutableStateFlow(
        TextFieldValue(
            value = _homeState.value.seekValue,
            onValueChange = this::setSeek

        )
    )
        private set

    fun createEcho() {

        _homeState.update { it.copy(isRecordingActivated = true) }
    }


    fun play(uri: Uri) {


        _homeState.update { it.copy(isPlaying = true) }
    }


    fun stop() {

        _homeState.update { it.copy(isPlaying = false) }

    }

    fun dismissRecordingModalSheet() {

        _homeState.update { it.copy(isRecordingActivated = false) }
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
        //  _homeState.update { it.copy(recordingUri = fileUri) }
    }

    fun stopRecording() {

        recorder.stop()
    }

    fun pauseRecording() {


    }

    fun setSeek(value: Float) {}

}

