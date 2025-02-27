package com.tonyxlab.echojournal.presentation.core.state

import androidx.compose.runtime.Stable
import com.tonyxlab.echojournal.utils.formatMillisToTime


@Stable
data class PlayerState(
    val duration: Int = 0,
    val currentPosition: Int = 0,
    val currentPositionText: String = "00:00",
    val mode: Mode = Mode.Stopped
) {

    val durationText = duration.toLong().formatMillisToTime()

    sealed interface Mode {

        data object Playing : Mode
        data object Paused : Mode
        data object Resumed : Mode
        data object Stopped : Mode
    }

}

