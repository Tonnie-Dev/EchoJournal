package com.tonyxlab.echojournal.presentation.core.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.util.fastForEach
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.presentation.core.utils.spacing

@Composable
fun MoodRow(
    activeMood: Mood,
    moods: List<Mood>,
    onMoodClick: (Mood) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        moods.fastForEach { mood ->
            MoodItem(
                icon =
                    if (activeMood == mood) {
                        mood.icon
                    } else {
                        mood.outlinedIcon
                    },
                title = mood.name,
                onClick = { onMoodClick(mood) },
            )
        }
    }
}

@Composable
fun MoodItem(
    @DrawableRes icon: Int,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .width(MaterialTheme.spacing.spaceExtraLarge)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceSmall),
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = title,
            modifier = Modifier.height(MaterialTheme.spacing.spaceTen * 4),
            contentScale = ContentScale.FillHeight,
        )

        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}