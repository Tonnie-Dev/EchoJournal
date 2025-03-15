package com.tonyxlab.echojournal.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.presentation.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.core.utils.LocalSpacing
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.utils.generateRandomEchoItem
import com.tonyxlab.echojournal.utils.toAmPmTime

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EchoCard(
    echo: Echo,
    isPlaying: Boolean,
    seekValue: Float,
    onSeek: (Float) -> Unit,
    onPlayPause: () -> Unit,
    onClickEcho: (String) -> Unit,
    modifier: Modifier = Modifier
) {


    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = MaterialTheme.spacing.spaceExtraSmall,
                RoundedCornerShape(spacing.spaceDoubleDp * 5),
                clip = false
            )
            .background(Color.White)
            .clickable {
                onClickEcho(echo.id)
            }
            .padding(
                top = spacing.spaceDoubleDp * 6,
                bottom = spacing.spaceDoubleDp * 7,
                start = spacing.spaceDoubleDp * 7,
                end = spacing.spaceDoubleDp * 7
            )
            .padding(spacing.spaceMedium),
        verticalArrangement = Arrangement.spacedBy(spacing.spaceDoubleDp * 3)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = spacing.spaceDoubleDp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = echo.title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = echo.timestamp.toAmPmTime(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        PlayTrackUnit(
            mood = echo.mood,
            isPlaying = isPlaying,
            seekValue = seekValue,
            onSeek = onSeek,
            echoLength = echo.audioDuration,
            onTogglePlay = onPlayPause,

            )


        if (echo.description.isNotEmpty()) {

            ExpandableText(echoText = echo.description)
        }

        with(echo.topics) {

            if (isNotEmpty()) {

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    Arrangement.spacedBy(MaterialTheme.spacing.spaceDoubleDp * 3),
                    maxLines = 1
                ) {
                    forEach { TopicChip(topic = it) }
                }
            }

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

                echo = generateRandomEchoItem(),
                isPlaying = true,
                seekValue = .6f,
                onSeek = {},
                onPlayPause = {},
                onClickEcho = {}
            )

            EchoCard(
                echo = generateRandomEchoItem(),
                isPlaying = false,
                seekValue = .7f,
                onSeek = {},
                onPlayPause = {},
                onClickEcho = {}
            )
        }

    }


}






