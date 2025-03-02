package com.tonyxlab.echojournal.presentation.core.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

data class GradientScheme(
    val buttonDefaultGradient: Brush = DefaultGradient,
    val buttonPressedGradient: Brush = PressedGradient
) {

    companion object {

        val DefaultGradient = Brush.linearGradient(
                colors = listOf(Color(0xFF578CFF), Color(0xFF1F70F5))
        )

        val PressedGradient = Brush.linearGradient(
                colors = listOf(Color(0xFF578CFF), Color(0xFF0057CC))
        )


        val FabRecordingBackground = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF3982F6).copy(alpha = 0.2f),
                Color(0xFF0E5FE0).copy(alpha = 0.2f)
            )
        )
        val FabPulsatingBackground = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF3982F6).copy(alpha = 0.1f),
                Color(0xFF0E5FE0).copy(alpha = 0.1f)
            )
        )
    }

}

val LocalGradient = staticCompositionLocalOf { GradientScheme() }


val MaterialTheme.gradient: GradientScheme
    @Composable @ReadOnlyComposable
    get() = LocalGradient.current