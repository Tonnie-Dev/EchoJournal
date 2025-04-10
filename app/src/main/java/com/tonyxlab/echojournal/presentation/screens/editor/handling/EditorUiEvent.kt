package com.tonyxlab.echojournal.presentation.screens.editor.handling

import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.domain.model.Topic
import com.tonyxlab.echojournal.presentation.core.base.handling.UiEvent
import java.io.File

sealed interface EditorUiEvent : UiEvent {
    data class BottomSheetOpened(val mood: Mood) : EditorUiEvent

    data object BottomSheetClosed : EditorUiEvent

    data class SheetConfirmClicked(val mood: Mood) : EditorUiEvent

    data class MoodSelected(val mood: Mood) : EditorUiEvent

    data class TitleValueChanged(val titleValue: String) : EditorUiEvent

    data class DescriptionValueChanged(val descriptionValue: String) : EditorUiEvent

    data class TopicValueChanged(val topicValue: String) : EditorUiEvent

    data class TagClearClicked(val topic: Topic) : EditorUiEvent

    data class TopicSelected(val topic: Topic) : EditorUiEvent

    data object CreateTopicClicked : EditorUiEvent

    data object PlayClicked : EditorUiEvent

    data object PauseClicked : EditorUiEvent

    data object ResumeClicked : EditorUiEvent

    data class SaveButtonClicked(val outDir: File) : EditorUiEvent

    data object ExitDialogToggled : EditorUiEvent

    data object ExitDialogConfirmClicked : EditorUiEvent
}