package com.tonyxlab.echojournal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.ui.theme.LocalSpacing


@Composable
fun PlayPauseButton(
    playArrowColor: Color,
    isPlaying: Boolean,
    onTogglePlay: () -> Unit,
    modifier: Modifier = Modifier,
    playButtonColor: Color = MaterialTheme.colorScheme.surface,
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color = playButtonColor)
            .clickable { onTogglePlay() }
            .padding(spacing.spaceExtraSmall),
        contentAlignment = Alignment.Center
    ) {


        if (isPlaying) {
            Icon(
                painter = painterResource(R.drawable.pause_arrow),
                contentDescription = stringResource(R.string.pause_text),
                tint = playArrowColor
            )
        } else {

            Icon(
                painter = painterResource(R.drawable.play_arrow),
                contentDescription = stringResource(R.string.play_text),
                tint = playArrowColor
            )
        }

    }

}
