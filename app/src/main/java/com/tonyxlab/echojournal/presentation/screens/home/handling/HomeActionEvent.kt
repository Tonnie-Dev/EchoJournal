package com.tonyxlab.echojournal.presentation.screens.home.handling

import com.tonyxlab.echojournal.presentation.core.base.handling.ActionEvent

sealed interface HomeActionEvent : ActionEvent {

    data class NavigateToEditorScreen(val audioFilePath: String, val echoId: Long) : HomeActionEvent

    data object DataLoaded : HomeActionEvent
}