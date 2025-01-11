package com.tonyxlab.echojournal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.LocalSpacing
import com.tonyxlab.echojournal.utils.generateLoremIpsum
import com.tonyxlab.echojournal.utils.renderSecondsToStrings

@Composable
fun EchoCard(
    echoName: String,
    echoText: String,
    mood: Mood,
    time: String,
    isPlaying: Boolean,
    seekValue: Float,
    onSeek: (Float) -> Unit,
    onTogglePlay: () -> Unit,
    echoLength: Int,
    modifier: Modifier = Modifier
) {


    val spacing = LocalSpacing.current
    Column(
            modifier = modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .shadow(
                            elevation = spacing.spaceSingleDp,
                            RoundedCornerShape(spacing.spaceDoubleDp * 5)
                    )
                    .padding(
                            top = spacing.spaceDoubleDp * 6,
                            bottom = spacing.spaceDoubleDp * 7,
                            start = spacing.spaceDoubleDp * 7,
                            end = spacing.spaceDoubleDp * 7
                    ),
            verticalArrangement = Arrangement.spacedBy(spacing.spaceDoubleDp * 3)
    ) {

        Row(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = spacing.spaceDoubleDp),
                horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                    text = echoName,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                    text = time,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Tracker(
                mood = mood,
                isPlaying = isPlaying,
                seekValue = seekValue,
                onSeek = onSeek,
                echoLength = echoLength,
                onTogglePlay = onTogglePlay
        )


        if (echoText.isNotEmpty()) {

            ExpandableText(echoText = echoText)
        }
    }
}

@Composable
private fun Tracker(
    mood: Mood,
    isPlaying: Boolean,
    seekValue: Float,
    onSeek: (Float) -> Unit,
    echoLength: Int,
    onTogglePlay: () -> Unit
) {
    val spacing = LocalSpacing.current
    Row(
            modifier = Modifier
                    .background(
                            color = mood.accentColor3,
                            RoundedCornerShape(spacing.spaceExtraLarge)
                    )
                    .fillMaxWidth()
                    .padding(
                            start = spacing.spaceExtraSmall,
                            end = spacing.spaceDoubleDp * 5,
                            top = spacing.spaceDoubleDp,
                            bottom = spacing.spaceDoubleDp
                    ),
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
private fun TrackerPreview() {
    val spacing = LocalSpacing.current
    EchoJournalTheme {

        Column(
                modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(spacing.spaceMedium),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {

            EchoCard(

                    echoName = "Work",
                    echoText = generateLoremIpsum(120),
                    mood = Mood.Neutral,
                    time = "09:13",
                    isPlaying = true,
                    seekValue = .6f,
                    onSeek = {},
                    onTogglePlay = {},
                    echoLength = 123,
            )

            EchoCard(
                    echoName = "Dinner Time",
                    echoText = generateLoremIpsum(120),
                    mood = Mood.Sad,
                    time = "09:13",
                    isPlaying = false,
                    seekValue = .7f,
                    onSeek = {},
                    onTogglePlay = {},
                    echoLength = 234,
            )
        }

    }
}


