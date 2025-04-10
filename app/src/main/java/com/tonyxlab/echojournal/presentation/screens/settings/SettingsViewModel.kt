package com.tonyxlab.echojournal.presentation.screens.settings

import androidx.lifecycle.viewModelScope
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.domain.model.Topic
import com.tonyxlab.echojournal.domain.model.toMood
import com.tonyxlab.echojournal.domain.repository.SettingsRepository
import com.tonyxlab.echojournal.domain.repository.TopicRepository
import com.tonyxlab.echojournal.presentation.core.base.BaseViewModel
import com.tonyxlab.echojournal.presentation.screens.settings.handling.SettingsActionEvent
import com.tonyxlab.echojournal.presentation.screens.settings.handling.SettingsUiEvent
import com.tonyxlab.echojournal.presentation.screens.settings.handling.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

typealias SettingsBaseViewModel = BaseViewModel<SettingsUiState, SettingsUiEvent, SettingsActionEvent>
@HiltViewModel
class SettingsViewModel
    @Inject
    constructor(
        private val topicRepository: TopicRepository,
        private val settingsRepository: SettingsRepository,
    ) : SettingsBaseViewModel() {
        override val initialState: SettingsUiState
            get() = SettingsUiState()

        private val searchQuery = MutableStateFlow("")

        private val searchResults: StateFlow<List<Topic>> =
            searchQuery.flatMapLatest { query ->

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
                SharingStarted.Companion.WhileSubscribed(5000),
                emptyList(),
            )

        init {
            launch {
                initializeDefaultSettings()
                observeTopicSearchResults()
            }
        }

        override fun onEvent(event: SettingsUiEvent) {
            when (event) {
                is SettingsUiEvent.TopicValueChange -> updatedTopicValue(event.topicValue)
                is SettingsUiEvent.ClearTagClick -> clearTopic(event.topic)
                is SettingsUiEvent.SelectTopic -> updateCurrentTopics(event.topic)
                SettingsUiEvent.CreateTopicClick -> addNewTopic()
                SettingsUiEvent.ToggleAddButton -> toggleAddButtonVisibility()
                is SettingsUiEvent.SelectMood -> selectMood(event.mood)
            }
        }

        private suspend fun initializeDefaultSettings() {
            val defaultMood = settingsRepository.getMood()
            val defaultTopicIds = settingsRepository.getTopics()
            val defaultTopics = topicRepository.getTopicsByIds(defaultTopicIds)
            updateState {
                it.copy(
                    activeMood = defaultMood.toMood(),
                    topicState = currentState.topicState.copy(currentTopics = defaultTopics),
                )
            }
        }

        private suspend fun observeTopicSearchResults() {
            searchResults.collect { topics: List<Topic> ->
                updateTopicState {
                    it.copy(foundTopics = topics)
                }
            }
        }

        private fun selectMood(mood: Mood) {
            val updatedMood = if (currentState.activeMood == mood) Mood.Undefined else mood
            updateState { it.copy(activeMood = updatedMood) }
            if (!currentState.topicState.isAddButtonVisible) {
                updateTopicState { it.copy(isAddButtonVisible = true) }
            }

            launch {
                settingsRepository.saveMood(updatedMood.name)
            }
        }

        private fun clearTopic(topic: Topic) {
            updateTopicState {
                it.copy(currentTopics = currentState.topicState.currentTopics.minus(topic))
            }
            saveTopicsSettings()
        }

        private fun toggleAddButtonVisibility() {
            updateTopicState {
                it.copy(isAddButtonVisible = !currentState.topicState.isAddButtonVisible)
            }
        }

        private fun updatedTopicValue(topic: String) {
            updateTopicState { it.copy(topicValue = topic) }
            searchQuery.value = topic
        }

        private fun updateCurrentTopics(newTopic: Topic) {
            updateTopicState {
                it.copy(
                    currentTopics =
                        currentState.topicState.currentTopics.plus(
                            newTopic,
                        ),
                    topicValue = "",
                    isAddButtonVisible = true,
                )
            }
            saveTopicsSettings()
        }

        private fun addNewTopic() {
            val newTopic = Topic(name = currentState.topicState.topicValue)
            updateCurrentTopics(newTopic)
            saveTopicsSettings()
            launch {
                topicRepository.insertTopic(newTopic)
            }
        }

        private fun saveTopicsSettings() {
            val topicIds = currentState.topicState.currentTopics.map { it.id }

            launch {
                settingsRepository.saveTopics(topicIds)
            }
        }

        private fun updateTopicState(update: (SettingsUiState.TopicState) -> SettingsUiState.TopicState) {
            updateState { it.copy(topicState = update(it.topicState)) }
        }
    }