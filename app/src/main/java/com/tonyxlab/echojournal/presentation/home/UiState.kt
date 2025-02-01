package com.tonyxlab.echojournal.presentation.home

import android.net.Uri
import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.domain.model.Mood

data class UiState(
    val echoes: List<Echo> = emptyList(),
    val savedTopics: List<String> = emptyList(),
    val topic: String = "",
    val currentTopics: List<String> = emptyList(),
    val description: String = "",
    val seekValue: Float = 0f,
    val title: String = "",
    val isPlaying: Boolean = false,
    val isRecordingActivated: Boolean = false,
    val isRecordingInProgress: Boolean = false,
    val recordingUri: Uri = Uri.EMPTY,
    val isShowMoodSelectionSheet: Boolean = false,
    val isShowMoodTitleIcon: Boolean = false,
    val mood: Mood = Mood.Other
) {

    var isMoodConfirmButtonHighlighted = mood != Mood.Other

}