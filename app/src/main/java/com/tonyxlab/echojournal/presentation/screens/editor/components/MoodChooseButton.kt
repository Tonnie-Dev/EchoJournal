package com.tonyxlab.echojournal.presentation.screens.editor.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.theme.Primary95
import com.tonyxlab.echojournal.presentation.theme.Secondary70

@Composable
fun MoodChooseButton(
    mood: Mood,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    if (mood == Mood.Undefined) {
        Box(
            modifier = modifier
                .size(MaterialTheme.spacing.spaceLarge)
                .clip(CircleShape)
                .background(color = Primary95, shape = CircleShape)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.text_choose_mood),
                tint = Secondary70
            )
        }

    } else {

        Image(
            modifier = Modifier
                .height(MaterialTheme.spacing.spaceLarge)
                .clickable { onClick() },
            painter = painterResource(mood.icon),
            contentDescription = stringResource(id = R.string.text_choose_mood),
            contentScale = ContentScale.FillHeight
        )
    }

}