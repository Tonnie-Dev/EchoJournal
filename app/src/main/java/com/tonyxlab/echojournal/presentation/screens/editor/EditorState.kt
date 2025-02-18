package com.tonyxlab.echojournal.presentation.screens.editor

import android.net.Uri
import com.tonyxlab.echojournal.domain.model.Mood

data class EditorState(

    val seekValue: Float = 0f,
    val title: String = "",
    val savedTopics: List<String> = emptyList(),
    val selectedTopics: List<String> = emptyList(),
    val topic: String = "",
    val description: String = "",
    val recordingUri: Uri = Uri.EMPTY,
    val isPlaying: Boolean = false,
    val isShowMoodSelectionSheet: Boolean = false,
    val isShowMoodTitleIcon: Boolean = false,
    val mood: Mood = Mood.Undefined
) {

    var isMoodConfirmButtonHighlighted = mood != Mood.Undefined

}
