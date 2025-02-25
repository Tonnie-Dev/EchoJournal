package com.tonyxlab.echojournal.presentation.screens.home.components

sealed interface EchoListPosition {
    data object Top:EchoListPosition
    data object Center:EchoListPosition
    data object Bottom:EchoListPosition
    data object Single:EchoListPosition
}