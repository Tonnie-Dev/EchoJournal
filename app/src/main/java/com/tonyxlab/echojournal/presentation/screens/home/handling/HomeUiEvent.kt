package com.tonyxlab.echojournal.presentation.screens.home.handling

import com.tonyxlab.echojournal.domain.model.Mood

sealed interface HomeUiEvent {

    data object MoodFilterToggle : HomeUiEvent
    data class MoodItemSelection(val mood: Mood) : HomeUiEvent
    data object MoodItemCleared : HomeUiEvent

    data object TopicFilterToggle : HomeUiEvent
    data class TopicItemSelection(val topic: String) : HomeUiEvent
    data object TopicItemCleared : HomeUiEvent

    data class PermissionDialogOpened(val isOpen: Boolean) : HomeUiEvent

    data object StartRecording : HomeUiEvent
    data object PauseRecording : HomeUiEvent
    data object ResumeRecording : HomeUiEvent
    data class StopRecording(val saveFile: Boolean) : HomeUiEvent

    data object ActionButtonStartRecording : HomeUiEvent
    data class ActionButtonStopRecording(val saveFile: Boolean = true)


    data class PlayEcho(val echoId: String) : HomeUiEvent
    data class PauseEcho(val echoId: String) : HomeUiEvent
    data class ResumeEcho(val echoId: String) : HomeUiEvent
}