package com.tonyxlab.echojournal.presentation.components

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
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.LocalSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VolumeSlider(
    value: Float,
    onValueChange: ((Float) -> Unit)?,
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
                        colors = SliderDefaults.colors(),
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
    EchoJournalTheme {
        Surface {
            VolumeSlider(
                    value = .5f,
                    onValueChange = {}
            )
        }
    }

}