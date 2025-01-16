package com.tonyxlab.echojournal.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

data class Gradients(
    val defaultGradient: Brush = GradientDefault,
    val pressedGradient: Brush = GradientPressed
) {


    companion object {

        val GradientDefault = Brush.linearGradient(
                colors = listOf(Color(0xFF578CFF), Color(0xFF1F70F5))
        )

        val GradientPressed = Brush.linearGradient(
                colors = listOf(Color(0xFF578CFF), Color(0xFF0057CC))
        )
    }

}

val LocalGradient = staticCompositionLocalOf { Gradients() }


val MaterialTheme.gradient: Gradients
    @Composable @ReadOnlyComposable
    get() = LocalGradient.current