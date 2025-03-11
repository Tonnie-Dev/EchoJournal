package com.tonyxlab.echojournal.presentation.core.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val spaceDefault: Dp = 0.dp,
    val spaceHalfDp: Dp = .5.dp,
    val spaceSingleDp: Dp = 1.dp,
    val spaceDoubleDp: Dp = 2.dp,
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val spaceTen: Dp = 10.dp,
    val spaceTwelve: Dp = 12.dp,
    val spaceMedium: Dp = 16.dp,
    val spaceLarge: Dp = 32.dp,
    val spaceLargeMedium: Dp = 48.dp,
    val spaceFifty: Dp = 50.dp,
    val spaceExtraLarge: Dp = 64.dp,
    val spaceOneHundred: Dp = 100.dp,
    val spaceOneTwenty: Dp = 120.dp,
    val spaceOneTwentyEight: Dp = 120.dp,
    val spaceOneHundredFifty: Dp = 150.dp,
    val spaceTwoHundred: Dp = 200.dp,
    val spaceTwoHundredFifty: Dp = 250.dp,
    val spaceFiveHundred: Dp = 500.dp

)

val LocalSpacing = staticCompositionLocalOf { Dimens() }

val MaterialTheme.spacing: Dimens
    @Composable @ReadOnlyComposable
    get() = LocalSpacing.current