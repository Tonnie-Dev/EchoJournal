package com.tonyxlab.echojournal.presentation.screens.home

import com.tonyxlab.echojournal.domain.model.Echo

data class HomeState(
    val echoes: List<Echo> = emptyList(),
    val seekValue: Float = 0f,
    val isPlaying: Boolean = false,
    val isRecordingActivated: Boolean = false,
    val isRecordingInProgress: Boolean = false,
)


