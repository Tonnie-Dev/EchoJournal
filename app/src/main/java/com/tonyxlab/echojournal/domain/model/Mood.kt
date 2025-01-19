package com.tonyxlab.echojournal.domain.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.ui.theme.Excited25
import com.tonyxlab.echojournal.presentation.ui.theme.Excited35
import com.tonyxlab.echojournal.presentation.ui.theme.Excited80
import com.tonyxlab.echojournal.presentation.ui.theme.Neutral25
import com.tonyxlab.echojournal.presentation.ui.theme.Neutral35
import com.tonyxlab.echojournal.presentation.ui.theme.Neutral80
import com.tonyxlab.echojournal.presentation.ui.theme.Peaceful25
import com.tonyxlab.echojournal.presentation.ui.theme.Peaceful35
import com.tonyxlab.echojournal.presentation.ui.theme.Peaceful80
import com.tonyxlab.echojournal.presentation.ui.theme.Sad25
import com.tonyxlab.echojournal.presentation.ui.theme.Sad35
import com.tonyxlab.echojournal.presentation.ui.theme.Sad80
import com.tonyxlab.echojournal.presentation.ui.theme.Stressed25
import com.tonyxlab.echojournal.presentation.ui.theme.Stressed35
import com.tonyxlab.echojournal.presentation.ui.theme.Stressed80

sealed class Mood(
    @DrawableRes val icon: Int,
    val name: String,
    val accentColor1: Color,
    val accentColor2: Color,
    val accentColor3: Color
) {

    data object Stressed : Mood(
            icon = R.drawable.mood_stressed,
            name = "Stressed",
            accentColor1 = Stressed80,
            accentColor2 = Stressed35,
            accentColor3 = Stressed25
    )

    data object Sad : Mood(
            icon = R.drawable.mood_sad,
            name = "Sad",
            accentColor1 = Sad80,
            accentColor2 = Sad35,
            accentColor3 = Sad25
    )

    data object Neutral : Mood(
            icon = R.drawable.mood_neutral,
            name = "Neutral",
            accentColor1 = Neutral80,
            accentColor2 = Neutral35,
            accentColor3 = Neutral25
    )

    data object Peaceful : Mood(
            icon = R.drawable.mood_peaceful,
            name = "Peaceful",
            accentColor1 = Peaceful80,
            accentColor2 = Peaceful35,
            accentColor3 = Peaceful25
    )

    data object Excited : Mood(
            icon = R.drawable.mood_excited,
            name = "Excited",
            accentColor1 = Excited80,
            accentColor2 = Excited35,
            accentColor3 = Excited25
    )
}