package com.tonyxlab.echojournal.presentation.home

import android.net.Uri
import com.tonyxlab.echojournal.domain.model.Echo

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
    val recordingUri: Uri = Uri.EMPTY
)