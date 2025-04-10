package com.tonyxlab.echojournal.presentation.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderDefaults.Track
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.presentation.core.utils.LocalSpacing
import com.tonyxlab.echojournal.presentation.theme.EchoJournalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Seeker(
    playbackPosition: Int,
    playbackDuration: Int,
    mood: Mood,
    onValueChange: ((Float) -> Unit)?,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current

    Slider(
        value = playbackPosition / playbackDuration.toFloat(),
        onValueChange = { onValueChange?.invoke(it) },
        modifier = modifier,
        thumb = {},
        track = {
            Track(
                modifier = Modifier.height(spacing.spaceExtraSmall),
                sliderState = it,
                colors =
                    SliderDefaults.colors(
                        activeTrackColor = mood.moodButtonColor,
                        inactiveTrackColor = mood.moodTrackColor,
                    ),
                thumbTrackGapSize = 0.dp,
                trackInsideCornerSize = 0.dp,
                drawStopIndicator = null,
            )
        },
    )
}

@PreviewLightDark
@Composable
private fun SliderPreview() {
    val spacing = LocalSpacing.current
    EchoJournalTheme {
        Surface {
            Column(verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)) {
                Seeker(
                    playbackPosition = 10,
                    playbackDuration = 100,
                    onValueChange = {},
                    mood = Mood.Excited,
                )
                Seeker(
                    playbackPosition = 50,
                    playbackDuration = 100,
                    onValueChange = {},
                    mood = Mood.Sad,
                )
                Seeker(
                    playbackPosition = 70,
                    playbackDuration = 100,
                    onValueChange = {},
                    mood = Mood.Neutral,
                )
            }
        }
    }
}