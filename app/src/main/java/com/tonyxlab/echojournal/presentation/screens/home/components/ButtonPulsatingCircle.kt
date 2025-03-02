package com.tonyxlab.echojournal.presentation.screens.home.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.tonyxlab.echojournal.presentation.core.utils.GradientScheme
import com.tonyxlab.echojournal.presentation.core.utils.spacing

@Composable

fun ButtonPulsatingCircle(
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
            animation = tween(durationMillis = 1_0000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Pulsating Circle Size"
    )

    Canvas(modifier = modifier.size(pulseSize)) {

       // Circle with Animation

        drawCircle(
            brush = GradientScheme.FabPulsatingBackground,
            radius = animatedSize/2,
            center = Offset(x = size.width/2, y = size.height/2)
        )

       // Circle without animation
        drawCircle(
            brush = GradientScheme.FabRecordingBackground,
            radius =baseSizePx/2,
            center = Offset(x = size.width/2, y = size.height)
        )
    }

}