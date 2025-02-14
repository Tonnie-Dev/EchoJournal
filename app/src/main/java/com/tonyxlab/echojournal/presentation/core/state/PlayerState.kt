package com.tonyxlab.echojournal.presentation.core.state

import androidx.compose.runtime.Stable
import com.tonyxlab.echojournal.utils.formatMillisToTime


@Stable
data class PlayerState(
    val duration: Int = 0,
    val currentPosition: Int = 0,
    val currentPositionText: String = "00:00",
    val action: Action = Action.Initializing
) {

    val durationText = duration.toLong().formatMillisToTime()

    sealed interface Action {

        data object Initializing : Action
        data object Playing : Action
        data object Paused : Action
        data object Resumed : Action
    }

}

