package com.tonyxlab.echojournal.presentation.screens.home.handling

import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.presentation.core.base.handling.UiEvent

sealed interface HomeUiEvent:UiEvent {

    data object ActivateMoodFilter : HomeUiEvent
    data class SelectMoodItem(val mood: Mood) : HomeUiEvent
    data object CancelMoodFilter : HomeUiEvent

    data object ActivateTopicFilter : HomeUiEvent
    data class SelectTopicItem(val topic: String) : HomeUiEvent
    data object CancelTopicFilter : HomeUiEvent

    data class OpenPermissionDialog(val isOpen: Boolean) : HomeUiEvent

    data object StartRecording : HomeUiEvent
    data object PauseRecording : HomeUiEvent
    data object ResumeRecording : HomeUiEvent
    data class StopRecording(val saveFile: Boolean) : HomeUiEvent

    data object ActionButtonStartRecording : HomeUiEvent
    data class ActionButtonStopRecording(val saveFile: Boolean = true):HomeUiEvent


    data class StartPlay(val echoId: String) : HomeUiEvent
    data class PausePlay(val echoId: String) : HomeUiEvent
    data class ResumePlay(val echoId: String) : HomeUiEvent
}