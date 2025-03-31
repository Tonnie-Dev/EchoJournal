package com.tonyxlab.echojournal.presentation.screens.settings.handling

import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.domain.model.Topic

sealed interface SettingsUiEvent {
    data class SelectMood(val mood: Mood) : SettingsUiEvent
    data class TopicValueChange(val topic: Topic) : SettingsUiEvent
    data class SelectTopic(val topic: Topic) : SettingsUiEvent
    data class ClearTagClick(val topic: Topic) : SettingsUiEvent
    data object CreateTopicClick : SettingsUiEvent
    data object ToggleAddButton : SettingsUiEvent
    data object NavigateBack : SettingsUiEvent
}