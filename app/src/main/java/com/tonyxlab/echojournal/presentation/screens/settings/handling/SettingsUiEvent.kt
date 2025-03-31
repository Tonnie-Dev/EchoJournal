package com.tonyxlab.echojournal.presentation.screens.settings.handling

import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.domain.model.Topic
import com.tonyxlab.echojournal.presentation.core.base.handling.UiEvent

sealed interface SettingsUiEvent: UiEvent {
    data class SelectMood(val mood: Mood) : SettingsUiEvent
    data class TopicValueChange(val topicValue: String) : SettingsUiEvent
    data class SelectTopic(val topic: Topic) : SettingsUiEvent
    data class ClearTagClick(val topic: Topic) : SettingsUiEvent
    data object CreateTopicClick : SettingsUiEvent
    data object ToggleAddButton : SettingsUiEvent

}