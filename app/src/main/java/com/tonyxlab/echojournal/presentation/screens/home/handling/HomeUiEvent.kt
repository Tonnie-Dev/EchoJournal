package com.tonyxlab.echojournal.presentation.screens.home.handling

import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.presentation.core.base.handling.UiEvent

sealed interface HomeUiEvent:UiEvent {

    data object ToggleMoodFilter : HomeUiEvent
    data class SelectMoodItem(val mood: String) : HomeUiEvent
    data object CancelMoodFilter : HomeUiEvent

    data object ToggleTopicFilter : HomeUiEvent
    data class SelectTopicItem(val topic: String) : HomeUiEvent
    data object CancelTopicFilter : HomeUiEvent

    data class OpenPermissionDialog(val isOpen: Boolean) : HomeUiEvent

    data object StartRecording : HomeUiEvent
    data object PauseRecording : HomeUiEvent
    data object ResumeRecording : HomeUiEvent
    data class StopRecording(val saveFile: Boolean) : HomeUiEvent

    data object ActionButtonStartRecording : HomeUiEvent
    data class ActionButtonStopRecording(val saveFile: Boolean = true):HomeUiEvent


    data class StartPlay(val echoId: Long) : HomeUiEvent
    data class PausePlay(val echoId: Long) : HomeUiEvent
    data class ResumePlay(val echoId: Long) : HomeUiEvent
}