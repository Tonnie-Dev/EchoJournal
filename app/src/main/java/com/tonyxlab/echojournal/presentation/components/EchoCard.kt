package com.tonyxlab.echojournal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.LocalSpacing
import com.tonyxlab.echojournal.utils.generateLoremIpsum

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

        PlayTrackUnit(
            mood = mood,
            isPlaying = isPlaying,
            seekValue = seekValue,
            onSeek = onSeek,
            echoLength = echoLength,
            onTogglePlay = onTogglePlay,

        )


        if (echoText.isNotEmpty()) {

            ExpandableText(echoText = echoText)
        }
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


