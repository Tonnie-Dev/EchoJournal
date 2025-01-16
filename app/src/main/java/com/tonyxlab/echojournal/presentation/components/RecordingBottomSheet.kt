package com.tonyxlab.echojournal.presentation.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.LocalSpacing
import com.tonyxlab.echojournal.presentation.ui.theme.Primary90
import com.tonyxlab.echojournal.presentation.ui.theme.Primary95
import com.tonyxlab.echojournal.presentation.ui.theme.gradient
import com.tonyxlab.echojournal.presentation.ui.theme.spacing


@Composable
fun BottomSheetContent(
    isPlaying: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    time: String = "01:13:40"
) {
    val spacing = LocalSpacing.current

    Box(
            modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.spaceFifty)
    ) {
        Column(
                modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                    text = if (isPlaying) stringResource(id = R.string.text_recording_memories) else stringResource(
                            id = R.string.text_recording_paused
                    ),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                    text = time,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Icon(
                modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .size(MaterialTheme.spacing.spaceSmall * 6)
                        .align(Alignment.CenterStart)
                        .padding(spacing.spaceSmall),
                imageVector = Icons.Default.Close,
                tint = MaterialTheme.colorScheme.onErrorContainer,
                contentDescription = stringResource(R.string.icon_close_text),

                )
        RecordingButton(
                isPlaying = isPlaying,
                onClick = onClick,
                modifier = Modifier.align(Alignment.Center)
        )

        Icon(
                modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onPrimaryContainer)
                        .size(spacing.spaceSmall * 6)
                        .align(Alignment.CenterEnd)
                        .padding(spacing.spaceSmall),
                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.Done,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(R.string.icon_close_text),

                )

    }


}

@Composable
fun RecordingButton(
    isPlaying: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gradient = MaterialTheme.gradient.buttonDefaultGradient
    val infiniteTransition = rememberInfiniteTransition()
    val innerCircleScaling by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                    animation = tween(1000),
                    repeatMode = RepeatMode.Reverse
            )
    )
    val outerCircleScaling by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.3f,
            animationSpec = infiniteRepeatable(
                    animation = tween(1000, delayMillis = 100),
                    repeatMode = RepeatMode.Reverse
            )
    )

    Box(modifier = modifier) {
        Canvas(
                modifier = Modifier
                        .size(MaterialTheme.spacing.spaceOneTwentyEight)
                        .clickable { onClick() }
        ) {
            if (isPlaying) {
                // Outer Circle
                drawCircle(
                        color = Primary95,
                        radius = (size.minDimension.div(2)) * outerCircleScaling,
                        alpha = 0.5f
                )
                //  Inner Circle
                drawCircle(
                        color = Primary90,
                        radius = (size.minDimension.div(2.2f)) * innerCircleScaling,
                        alpha = 0.7f
                )
            }

            // FAB
            drawCircle(
                    brush = gradient,
                    radius = size.minDimension.div(3)
            )
        }

        // Icon
        Box(
                modifier = Modifier
                        .size(MaterialTheme.spacing.spaceLarge)
                        .align(Alignment.Center),
                contentAlignment = Alignment.Center
        ) {
            Icon(
                    imageVector = if (isPlaying) Icons.Default.Done else Icons.Default.Mic,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun BottomSheetContentPreview() {


    EchoJournalTheme {


        Column(
                modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .fillMaxSize()
                        .padding(vertical = MaterialTheme.spacing.spaceFifty),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceExtraLarge)
        ) {


            BottomSheetContent(
                    isPlaying = true,
                    onClick = {},
                    modifier = Modifier.fillMaxHeight(.3f)
            )

            BottomSheetContent(
                    isPlaying = false,
                    onClick = {},
                    modifier = Modifier.fillMaxHeight(.3f)
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun BottomSheetLayoutPreview() {

    EchoJournalTheme {

        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(vertical = MaterialTheme.spacing.spaceTwoHundred),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {
            RecordingButton(isPlaying = true, onClick = {})
            RecordingButton(isPlaying = false, onClick = {})
        }
    }

}