package com.tonyxlab.echojournal.presentation.screens.editor.handling


import androidx.compose.runtime.Stable
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.domain.model.Topic
import com.tonyxlab.echojournal.presentation.core.base.handling.UiState
import com.tonyxlab.echojournal.presentation.core.state.PlayerState

@Stable
data class EditorUiState(
    val currentMood: Mood = Mood.Undefined,
    val titleValue: String = "",
    val topicValue: String = "",
    val descriptionValue: String = "",
    val playerState: PlayerState = PlayerState(),
    val currentTopics: List<Topic> = emptyList(),
    val foundTopics: List<Topic> = emptyList(),
    val editorSheetState: EditorSheetState = EditorSheetState(),
    val showExitDialog: Boolean = false
): UiState{

    val isSaveEnabled: Boolean
        get() = titleValue.isNotBlank() && currentMood != Mood.Undefined

    @Stable
    data class EditorSheetState(
        val isSheetOpen: Boolean = false,
        val activeMood: Mood = Mood.Undefined,
        val moods: List<Mood> = Mood.allMoods()
    )
}