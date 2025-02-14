package com.tonyxlab.echojournal.presentation.screens.editor.handling

import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.presentation.core.base.handling.UiEvent

sealed interface EditorUiEvent:UiEvent {

    data object BottomSheetOpened: EditorUiEvent
    data object BottomSheetClosed: EditorUiEvent
   data class MoodSelected(val mood:Mood): EditorUiEvent


}