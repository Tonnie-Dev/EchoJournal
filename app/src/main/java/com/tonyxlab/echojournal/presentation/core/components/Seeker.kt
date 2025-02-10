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
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.LocalSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Seeker(
    value: Float,
    onValueChange: ((Float) -> Unit)?,
    mood: Mood,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current

    Slider(
            value = value,
            onValueChange = { onValueChange?.invoke(it) },
            modifier = modifier,
            thumb = {},
            track = {
                Track(
                        modifier = Modifier.height(spacing.spaceExtraSmall),
                        sliderState = it,
                        colors = SliderDefaults.colors(
                                activeTrackColor = mood.accentColor1,
                                inactiveTrackColor = mood.accentColor2
                        ),
                        thumbTrackGapSize = 0.dp,
                        trackInsideCornerSize = 0.dp,
                        drawStopIndicator = null

                )
            }
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
                        value = .5f,
                        onValueChange = {},
                        mood = Mood.Excited,
                )

                Seeker(
                        value = .4f,
                        onValueChange = {},
                        mood = Mood.Sad,
                )

                Seeker(
                        value = .7f,
                        onValueChange = {},
                        mood = Mood.Neutral,
                )
            }
        }
    }

}