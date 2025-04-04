package com.tonyxlab.echojournal.presentation.screens.home.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tonyxlab.echojournal.presentation.core.utils.GradientScheme
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.theme.EchoJournalTheme

@Composable

fun PulsatingButton(
    modifier: Modifier = Modifier,
    baseSize: Dp = MaterialTheme.spacing.spaceOneHundred + MaterialTheme.spacing.spaceSmall,
    pulseSize: Dp = MaterialTheme.spacing.spaceOneTwentyEight,
) {

    val infiniteTransition = rememberInfiniteTransition(label = "Pulsating Circle Transition")
    val density = LocalDensity.current

    val baseSizePx = with(density) { baseSize.toPx() }
    val pulsatingSizePx = with(density) { pulseSize.toPx() }

    val animatedSize by infiniteTransition.animateFloat(
        initialValue = baseSizePx,
        targetValue = pulsatingSizePx,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Pulsating Circle Size"
    )

    Canvas(modifier = modifier.size(59.dp)) {

        // Circle with Animation
        drawCircle(
            brush = GradientScheme.FabPulsatingBackground,
            radius = animatedSize / 2,
            center = Offset(x = size.width / 2, y = size.height / 2)
        )

        // Circle without animation
        drawCircle(
            brush = GradientScheme.FabRecordingBackground,
            radius = baseSize.toPx()/ 2,
            center = Offset(x = size.width / 2, y = size.height/2)
        )
    }

}

@PreviewLightDark
@Composable
private fun PulsatingButtonPreview() {

    EchoJournalTheme {

        Column(
            modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

           PulsatingButton()

        }
    }

}