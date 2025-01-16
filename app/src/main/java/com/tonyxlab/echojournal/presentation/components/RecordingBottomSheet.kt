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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
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
fun BottomSheetContent(modifier: Modifier = Modifier) {

}

@Composable
fun BottomSheetLayout(modifier: Modifier = Modifier) {
    val spacing = LocalSpacing.current

    Box(modifier = Modifier.fillMaxWidth()) {
        Icon(
                modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .size(spacing.spaceSmall * 6)
                        .align(Alignment.CenterStart)
                        .padding(spacing.spaceSmall),
                imageVector = Icons.Default.Close,
                tint = MaterialTheme.colorScheme.onErrorContainer,
                contentDescription = stringResource(R.string.icon_close_text),

                )


        Icon(
                modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onPrimary)
                        .size(spacing.spaceSmall * 6)
                        .align(Alignment.CenterEnd)
                        .padding(spacing.spaceSmall),
                painter = painterResource(R.drawable.pause_arrow),
                tint = MaterialTheme.colorScheme.primaryContainer,
                contentDescription = stringResource(R.string.icon_close_text),

                )

    }


}

@Composable
fun RecordingButton(
    isPlaying: Boolean,
    onClick: () -> Unit,
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

    Box {
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