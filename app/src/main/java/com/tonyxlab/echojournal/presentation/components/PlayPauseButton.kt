package com.tonyxlab.echojournal.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.Excited80
import com.tonyxlab.echojournal.presentation.ui.theme.LocalSpacing
import com.tonyxlab.echojournal.presentation.ui.theme.Neutral80


@Composable
fun PlayPauseButton(
    playBarsColor: Color,
    playButtonColor: Color = MaterialTheme.colorScheme.surface,
    isPlaying: Boolean,
    onTogglePlay: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    Box(
            modifier = modifier

                    .clip(CircleShape)
                    .background(color = playButtonColor)
                   // .background(color = playButtonColor, shape = CircleShape)
                    //.shadow(2.dp)
                    .clickable { onTogglePlay() }

                    .padding(spacing.spaceExtraSmall *3)


            ,
            contentAlignment = Alignment.Center
    ) {

        Canvas(
                modifier = Modifier
                        .size(spacing.spaceMedium)
                        .align(Alignment.Center)
        ) {

            if (isPlaying)
                drawPauseBars(playBarsColor)
            else
                drawPlayTriangle(playBarsColor)
        }
    }

}

private fun DrawScope.drawPauseBars(color: Color) {

    val barWidth = 4.dp.toPx()
    val spacing = 4.dp.toPx()
    val barHeight = size.height



    drawRect(
            color = color,
            topLeft = Offset((size.width - (barWidth * 2 + spacing)) / 2, 0f),
            size = Size(barWidth, barHeight)
    )

    drawRect(
            color = color,
            topLeft = Offset((size.width / 2 + spacing / 2), 0f),
            size = Size(barWidth, barHeight)
    )
}


private fun DrawScope.drawPlayTriangle(color: Color) {

    val path = Path().apply {

        moveTo(size.width * 0.3f, size.height * .1f)
        lineTo(size.width * 1f, size.height /2)
        lineTo(size.width * 0.3f, size.height * .9f)
        close()
    }

    drawPath(path = path, color = color)
}

@PreviewLightDark
@Composable
private fun PlayPauseButtonPreview() {
    val spacing = LocalSpacing.current
    EchoJournalTheme {

        Column(
                modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(vertical = spacing.spaceTwoHundred),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceLarge),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {

            PlayPauseButton(
                    modifier = Modifier,
                    playBarsColor = Excited80,
                    isPlaying = true,
                    onTogglePlay = {},
            )

            PlayPauseButton(
                    playBarsColor = Neutral80,
                    isPlaying = false,
                    onTogglePlay = {},
            )
        }
    }
}