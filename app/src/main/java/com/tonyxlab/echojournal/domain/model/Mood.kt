package com.tonyxlab.echojournal.domain.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.theme.Excited25
import com.tonyxlab.echojournal.presentation.theme.Excited35
import com.tonyxlab.echojournal.presentation.theme.Excited80
import com.tonyxlab.echojournal.presentation.theme.Neutral25
import com.tonyxlab.echojournal.presentation.theme.Neutral35
import com.tonyxlab.echojournal.presentation.theme.Neutral80
import com.tonyxlab.echojournal.presentation.theme.Peaceful25
import com.tonyxlab.echojournal.presentation.theme.Peaceful35
import com.tonyxlab.echojournal.presentation.theme.Peaceful80
import com.tonyxlab.echojournal.presentation.theme.Primary30
import com.tonyxlab.echojournal.presentation.theme.Primary95
import com.tonyxlab.echojournal.presentation.theme.Sad25
import com.tonyxlab.echojournal.presentation.theme.Sad35
import com.tonyxlab.echojournal.presentation.theme.Sad80
import com.tonyxlab.echojournal.presentation.theme.Secondary80
import com.tonyxlab.echojournal.presentation.theme.Stressed25
import com.tonyxlab.echojournal.presentation.theme.Stressed35
import com.tonyxlab.echojournal.presentation.theme.Stressed80
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
sealed class Mood(
    @DrawableRes
    val icon: Int,
    val name: String,
    @DrawableRes
    val outlinedIcon: Int,
    @Serializable(with = ColorSerializer::class)
    val accentColor1: Color,
    @Serializable(with = ColorSerializer::class)
    val accentColor2: Color,
    @Serializable(with = ColorSerializer::class)
    val accentColor3: Color
) {

    @Serializable
    data object Stressed : Mood(
        icon = R.drawable.mood_stressed,
        outlinedIcon = R.drawable.mood_stressed_outline,
        name = "Stressed",
        accentColor1 = Stressed80,
        accentColor2 = Stressed35,
        accentColor3 = Stressed25
    )

    @Serializable
    data object Sad : Mood(
        icon = R.drawable.mood_sad,
        outlinedIcon = R.drawable.mood_sad_outline,
        name = "Sad",
        accentColor1 = Sad80,
        accentColor2 = Sad35,
        accentColor3 = Sad25
    )

    @Serializable
    data object Neutral : Mood(
        icon = R.drawable.mood_neutral,
        outlinedIcon = R.drawable.mood_neutral_outline,
        name = "Neutral",
        accentColor1 = Neutral80,
        accentColor2 = Neutral35,
        accentColor3 = Neutral25
    )

    @Serializable
    data object Peaceful : Mood(
        icon = R.drawable.mood_peaceful,
        outlinedIcon = R.drawable.mood_peaceful_outline,
        name = "Peaceful",
        accentColor1 = Peaceful80,
        accentColor2 = Peaceful35,
        accentColor3 = Peaceful25
    )

    @Serializable
    data object Excited : Mood(
        icon = R.drawable.mood_excited,
        outlinedIcon = R.drawable.mood_excited_outline,
        name = "Excited",
        accentColor1 = Excited80,
        accentColor2 = Excited35,
        accentColor3 = Excited25
    )

    @Serializable
    data object Other : Mood(
        icon = R.drawable.mood_excited,
        outlinedIcon = R.drawable.mood_excited_outline,
        name = "Other",
        accentColor1 = Primary30,
        accentColor2 = Secondary80,
        accentColor3 = Primary95
    )

    companion object {

        fun allMoods():List<Mood > = listOf()
    }
}

object ColorSerializer : KSerializer<Color> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Color", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: Color) {
        val argb = value.toArgb()
        encoder.encodeInt(argb)
    }

    override fun deserialize(decoder: Decoder): Color {
        val argb = decoder.decodeInt()
        return Color(argb)
    }
}
