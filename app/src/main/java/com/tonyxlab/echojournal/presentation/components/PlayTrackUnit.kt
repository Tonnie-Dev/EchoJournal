package com.tonyxlab.echojournal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.LocalSpacing
import com.tonyxlab.echojournal.presentation.ui.theme.spacing
import com.tonyxlab.echojournal.utils.renderSecondsToStrings


@Composable
fun PlayTrackUnit(
    seekValue: Float,
    onSeek: (Float) -> Unit,
    echoLength: Int,
    onTogglePlay: () -> Unit,
    modifier: Modifier = Modifier,
    mood: Mood = Mood.Other,
    isPlaying: Boolean = false,
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .background(
                color = mood.accentColor3,
                RoundedCornerShape(spacing.spaceExtraLarge)
            )
            .padding(
                start = MaterialTheme.spacing.spaceExtraSmall,
                end = MaterialTheme.spacing.spaceDoubleDp * 5,
                top = MaterialTheme.spacing.spaceDoubleDp,
                bottom = MaterialTheme.spacing.spaceDoubleDp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(spacing.spaceSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {

        PlayPauseButton(
            modifier = Modifier
                .size(spacing.spaceLarge + spacing.spaceSmall)
                .shadow(spacing.spaceSmall, CircleShape),
            playArrowColor = mood.accentColor1,
            isPlaying = isPlaying,
            onTogglePlay = onTogglePlay
        )
        Seeker(
            modifier = Modifier.weight(1f),
            value = seekValue,
            onValueChange = onSeek,
            mood = mood
        )

        Text(
            text = stringResource(
                R.string.live_tracker_timestamp,
                renderSecondsToStrings(echoLength / 2),
                renderSecondsToStrings(echoLength)
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


@PreviewLightDark
@Composable
private fun PlayTrackUnitPreview() {


    EchoJournalTheme {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(top = MaterialTheme.spacing.spaceExtraLarge),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {


// Appearance on Echo Card
            PlayTrackUnit(
                mood = Mood.Neutral,
                isPlaying = false,
                seekValue = 0.5f,
                onSeek = {},
                echoLength = 80,
                onTogglePlay = {},
            )

            // Appearance On Save Screen
            PlayTrackUnit(
                mood = Mood.Other,
                isPlaying = false,
                seekValue = 0.3f,
                onSeek = {},
                echoLength = 63,
                onTogglePlay = {},

                )
        }
    }
}


