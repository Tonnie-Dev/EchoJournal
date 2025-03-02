package com.tonyxlab.echojournal.presentation.screens.home.components

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import com.tonyxlab.echojournal.presentation.core.utils.Gradients
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest


@Composable
private fun RecordButton(
    isRecording: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onLongPressRelease: () -> Unit,
    modifier: Modifier = Modifier,
    buttonSize: Dp = MaterialTheme.spacing.spaceExtraSmall
) {

    val interactionSource = remember { MutableInteractionSource() }
    val viewConfiguration = LocalViewConfiguration.current
    var isLongClick by remember { mutableStateOf(false) }


    LaunchedEffect(interactionSource) {

        interactionSource.interactions.collectLatest { interaction ->

            when (interaction) {

                is PressInteraction.Press -> {
                    isLongClick = false
                    delay(viewConfiguration.longPressTimeoutMillis)
                    isLongClick = true
                    onLongClick()
                }

                is PressInteraction.Release -> {

                    if (!isLongClick) onClick() else onLongPressRelease()
                }

                is PressInteraction.Cancel -> {

                    isLongClick = false

                }
            }


        }
    }

    Surface(
        onClick = {},
        modifier = modifier.semantics { role = Role.Button },
        shape = CircleShape,
        color = Color.Transparent,
        contentColor = Color.Transparent,
        tonalElevation = MaterialTheme.spacing.spaceDoubleDp * 3,
        shadowElevation = MaterialTheme.spacing.spaceDoubleDp * 3,
        interactionSource = interactionSource
    ) {

        Box(
            modifier = Modifier
                .size(buttonSize)
                .clip(CircleShape)
                .background(
                    brush = Gradients.GradientPressed,
                    shape = CircleShape
                ), contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isRecording) Icons.Default.Mic else Icons.Default.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }


}

private fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).apply(::startActivity)
}