package com.tonyxlab.echojournal.presentation.screens.entry.handling

import com.tonyxlab.echojournal.presentation.core.base.handling.ActionEvent


sealed interface EntryActionEvent:ActionEvent
    data object NavigateBack:EntryActionEvent

