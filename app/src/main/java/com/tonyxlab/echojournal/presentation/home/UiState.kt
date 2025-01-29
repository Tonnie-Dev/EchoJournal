package com.tonyxlab.echojournal.presentation.home

data class UiState(
    val seekValue: Float = 0f,
    val isPlaying: Boolean = false,
    val isRecordingActivated: Boolean = false,
    val isRecordingInProgress:Boolean = false
)