package com.tonyxlab.echojournal.presentation.editor

import android.net.Uri
import com.tonyxlab.echojournal.domain.model.Mood

data class EditorState(

    val seekValue: Float = 0f,
    val title: String = "",
    val selectedTopics:Set<String> = emptySet(),
    val savedTopics: Set<String> = emptySet(),
    val topic: String = "",
    val currentTopics: List<String> = emptyList(),
    val description: String = "",
    val recordingUri: Uri = Uri.EMPTY,
    val isPlaying:Boolean = false,
    val isShowMoodSelectionSheet: Boolean = false,
    val isShowMoodTitleIcon: Boolean = false,
    val mood: Mood = Mood.Other
) {

    var isMoodConfirmButtonHighlighted = mood != Mood.Other

}
