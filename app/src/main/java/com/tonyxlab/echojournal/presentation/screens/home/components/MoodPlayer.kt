package com.tonyxlab.echojournal.presentation.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.presentation.core.state.PlayerState
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.theme.EchoJournalTheme

@Composable
fun MoodPlayer(
    mood: Mood,
    playerState: PlayerState,
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit,
    onResumeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = CircleShape,
        color = mood.moodBackgroundColor
    ) {
        Row(
            modifier = Modifier.padding(MaterialTheme.spacing.spaceExtraSmall),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Play Button
            Surface(
                modifier = Modifier
                    .size(MaterialTheme.spacing.spaceLarge)
                    .shadow(
                        MaterialTheme.spacing.spaceExtraSmall, CircleShape
                    )
                    .clip(CircleShape)
                    .clickable {
                        when (playerState.mode) {
                            PlayerState.Mode.Stopped -> onPlayClick()
                            PlayerState.Mode.Paused -> onResumeClick()
                            PlayerState.Mode.Playing -> onPauseClick()
                            PlayerState.Mode.Resumed -> onPauseClick()
                        }
                    }, shape = CircleShape
            ) {


                Icon(
                    modifier = Modifier.padding(MaterialTheme.spacing.spaceExtraSmall),
                    imageVector = when (playerState.mode) {
                        PlayerState.Mode.Stopped -> Icons.Default.PlayArrow
                        PlayerState.Mode.Paused -> Icons.Default.PlayArrow
                        PlayerState.Mode.Playing -> Icons.Default.Pause
                        PlayerState.Mode.Resumed -> Icons.Default.Pause
                    },
                    contentDescription = stringResource(id = R.string.play_text),
                    tint = mood.moodButtonColor
                )
            }
            PlayerTimer(
                modifier = Modifier.padding(end = MaterialTheme.spacing.spaceExtraSmall),
                duration = playerState.durationText,
                currentPosition = playerState.currentPositionText
            )


        }
    }

}

@Composable
fun PlayerTimer(
    duration: String,
    currentPosition: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        val placeholderText = if (duration.length > 5) "00:00:00" else "00:00"

        // Current Position
        Box(
            modifier = Modifier.width(IntrinsicSize.Max)
        ) {

            Text(
                text = currentPosition,
                style = MaterialTheme.typography.labelMedium
            )


            // Hidden placeholder text to define a fixed width.
            Text(
                text = placeholderText,
                style = MaterialTheme.typography.labelMedium.copy(color = Color.Transparent)
            )
        }

        // Duration
        Box(modifier = Modifier.width(IntrinsicSize.Max)) {

            Text(
                text = "/$duration",
                style = MaterialTheme.typography.labelMedium
            )

            // Hidden placeholder text to define a fixed width

            Text(
                text = "/$placeholderText",
                style = MaterialTheme.typography.labelMedium.copy(color = Color.Transparent)
            )
        }

    }
}

@PreviewLightDark
@Composable
private fun MoodPlayerPreview() {


    val playerState = remember {
        PlayerState(
            duration = 85,
            currentPosition = 58,
            mode = PlayerState.Mode.Playing
        )
    }
    EchoJournalTheme {


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            MoodPlayer(
                mood = Mood.Stressed,
                playerState = playerState,
                onPauseClick = {},
                onResumeClick = {},
                onPlayClick = {},
            )

        }
    }


}