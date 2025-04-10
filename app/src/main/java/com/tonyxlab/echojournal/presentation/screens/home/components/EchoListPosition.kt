package com.tonyxlab.echojournal.presentation.screens.home.components

sealed interface EchoListPosition {
    data object First : EchoListPosition

    data object Middle : EchoListPosition

    data object Last : EchoListPosition

    data object Single : EchoListPosition
}