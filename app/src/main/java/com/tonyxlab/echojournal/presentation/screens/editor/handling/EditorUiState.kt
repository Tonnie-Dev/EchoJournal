package com.tonyxlab.echojournal.presentation.screens.editor.handling

import android.adservices.topics.Topic
import androidx.compose.runtime.Stable
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.presentation.core.state.PlayerState

@Stable
data class EditorUiState(
    val currentMood: Mood = Mood.Undefined,
    val titleValue: String = "",
    val topicValue: String = "",
    val descriptionValue: String = "",
    val playerState: PlayerState = PlayerState(),
    val currentTopics: List<String> = emptyList(),
    val foundTopics: List<Topic> = emptyList(),
    val editorSheetState: EditorSheetState = EditorSheetState(),
    val isShowExitDialog: Boolean = false
) {

    val isSaveEnabled: Boolean
        get() = titleValue.isNotBlank() && currentMood != Mood.Undefined

    @Stable
    data class EditorSheetState(
        val isSheetOpen: Boolean = false,
        val activeMood: Mood = Mood.Undefined,
        val moods: List<Mood> = Mood.allMoods()
    )
}