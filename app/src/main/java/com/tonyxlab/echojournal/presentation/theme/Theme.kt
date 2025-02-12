package com.tonyxlab.echojournal.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.tonyxlab.echojournal.presentation.core.utils.Dimens
import com.tonyxlab.echojournal.presentation.core.utils.Gradients
import com.tonyxlab.echojournal.presentation.core.utils.LocalGradient
import com.tonyxlab.echojournal.presentation.core.utils.LocalSpacing

@Composable
fun EchoJournalTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
            LocalGradient provides Gradients(),
            LocalSpacing provides Dimens()
    ) {
        MaterialTheme(
                colorScheme = DefaultScheme,
                shapes = shapes,
                typography = Typography,
                content = content
        )
    }
}

private val DefaultScheme = lightColorScheme(
        primary = Primary30,
        primaryContainer = Primary50,
        onPrimary = Primary100,
        onPrimaryContainer = Primary95,
        secondary = Secondary30,
        secondaryContainer = Secondary50,
        background = NeutralVariant99,
        surface = Primary100,
        inverseOnSurface = Secondary80,
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