package com.tonyxlab.echojournal.presentation.screens.home.handling

sealed interface HomeActionEvent {

    data class NavigateToEditorScreen( val audioFilePath:String):HomeActionEvent
    data object DataLoaded:HomeActionEvent

}