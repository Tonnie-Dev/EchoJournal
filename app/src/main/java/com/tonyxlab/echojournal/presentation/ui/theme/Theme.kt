package com.tonyxlab.echojournal.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable

fun EchoJournalTheme(content: @Composable () -> Unit) {
    MaterialTheme(
            colorScheme = DefaultScheme,
            shapes = shapes,
            typography = Typography,
            content = content
    )
}

private val DefaultScheme = lightColorScheme(
        primary = Primary30,
        primaryContainer = Primary50,
        secondary = Secondary30,
        background = NeutralVariant99,
        surface = Primary100,
        onPrimary = Primary100,
        inverseOnSurface = Secondary80,
        secondaryContainer = Secondary50,
        onSurface = NeutralVariant10,
        onSurfaceVariant = NeutralVariant30,
        outline = NeutralVariant50,
        outlineVariant = NeutralVariant80,
        surfaceVariant = SurfaceVariant,
        surfaceTint = SurfaceTint12,
        inverseSurface = Secondary95,
        onErrorContainer = Error20,
        errorContainer = Error95,
        onError = Error100


)