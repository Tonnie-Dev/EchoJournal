package com.tonyxlab.echojournal.presentation.screens.home.handling

import androidx.compose.runtime.Stable
import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.presentation.core.base.handling.UiState
import com.tonyxlab.echojournal.presentation.core.state.PlayerState
import com.tonyxlab.echojournal.utils.Constants
import kotlinx.datetime.Instant
import timber.log.Timber


@Stable
data class HomeUiState(
    val echoes: Map<Instant, List<EchoHolderState>> = mapOf(),
    val filterState: FilterState = FilterState(),
    val isFilterActive: Boolean = false,
    val recordingSheetState: RecordingSheetState = RecordingSheetState(),
    val isPermissionDialogOpen: Boolean = false
) : UiState {


    @Stable
    data class EchoHolderState(
        val echo: Echo,
        val playerState: PlayerState = PlayerState(duration = echo.audioDuration)
    )

    @Stable
    data class RecordingSheetState(
        val isVisible: Boolean = false,
        val isRecording: Boolean = true,
        val recordingTime: String = Constants.DEFAULT_FORMATTED_TIME
    )

    @Stable
    data class FilterState(
        val isMoodFilterOpen: Boolean = false,
        val isTopicFilterOpen: Boolean = false,
        val moodFilterItems: List<FilterItem> = Mood.allMoods().map { FilterItem(title = it.name) },
        val topicFilterItems: List<FilterItem> = listOf()
    ) {
       
        data class FilterItem(
            val title: String = "",
            val isChecked: Boolean = false
        )
    }
}
