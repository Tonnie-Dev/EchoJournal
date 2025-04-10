package com.tonyxlab.echojournal.presentation.screens.settings.handling

import androidx.compose.ui.unit.IntOffset
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.domain.model.Topic
import com.tonyxlab.echojournal.presentation.core.base.handling.UiState

data class SettingsUiState(
    val activeMood: Mood = Mood.Undefined,
    val moods: List<Mood> = Mood.allMoods(),
    val topicState: TopicState = TopicState(),
) : UiState {

    data class TopicState(
        val topicValue: String = "",
        val currentTopics: List<Topic> = listOf(),
        val foundTopics: List<Topic> = listOf(),
        val topicDropdownOffset: IntOffset = IntOffset.Zero,
        val isAddButtonVisible: Boolean = true,
    )
}
