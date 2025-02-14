package com.tonyxlab.echojournal.presentation.screens.editor.handling

import com.tonyxlab.echojournal.presentation.core.base.handling.ActionEvent


sealed interface EditorActionEvent:ActionEvent {

    data object NavigateBack : EditorActionEvent

}

