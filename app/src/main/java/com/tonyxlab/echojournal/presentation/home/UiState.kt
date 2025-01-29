package com.tonyxlab.echojournal.presentation.home

import com.tonyxlab.echojournal.domain.model.Echo

data class UiState(
    val echoes: List<Echo> = emptyList(),
    val seekValue: Float = 0f,
    val title: String = "",
    val topic: String = "",
    val description: String = "",
    val isPlaying: Boolean = false,
    val isRecordingActivated: Boolean = false,
    val isRecordingInProgress: Boolean = false
)