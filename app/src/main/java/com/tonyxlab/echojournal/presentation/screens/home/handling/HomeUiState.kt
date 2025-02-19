package com.tonyxlab.echojournal.presentation.screens.home.handling

import androidx.compose.runtime.Stable
import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.presentation.core.base.handling.UiState
import com.tonyxlab.echojournal.presentation.core.state.PlayerState
import com.tonyxlab.echojournal.utils.Constants


@Stable
data class HomeUiState(
    val echoes: Map<Long, List<EchoHolderState>> = mapOf(),
    val filterState: FilterState = FilterState(),
    val isFilterActive: Boolean = false,
    val homeSheetState: HomeSheetState = HomeSheetState(),
    val isPermissionDialogOpen: Boolean = false
) : UiState {


    @Stable
    data class EchoHolderState(
        val echo: Echo,
        val playerState: PlayerState = PlayerState(duration = echo.duration)
    )

    @Stable
    data class HomeSheetState(
        val isVisibleBoolean: Boolean = false,
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
