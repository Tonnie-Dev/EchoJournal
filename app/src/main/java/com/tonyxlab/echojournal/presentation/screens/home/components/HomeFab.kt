package com.tonyxlab.echojournal.presentation.screens.home.components

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.core.utils.GradientScheme
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeFab(
    onResult: (isGranted: Boolean, isLongClicked: Boolean) -> Unit,
    onLongPressRelease: (isCancelled: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    buttonSize: Dp = MaterialTheme.spacing.spaceExtraLarge,
    pulsatingCircleSize: Dp = MaterialTheme.spacing.spaceOneTwentyEight
) {

    val context = LocalContext.current
    val activity = context as Activity

    var isPermissionDialogOpen by remember { mutableStateOf(false) }
    var isLongPressed by remember { mutableStateOf(false) }

    var dragOffsetX by remember { mutableStateOf(0f) }
    val hapticFeedback = LocalHapticFeedback.current
    var isEntryCancelled by remember { mutableStateOf(false) }


    val recordAudioPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->

            isPermissionDialogOpen = !isGranted
            onResult(isGranted, isLongPressed)
        }
    )

    // Animate size of the cancel icon based on drag effect
    val cancelIconScale by animateFloatAsState(
        targetValue = 1f + (-dragOffsetX / 200f).coerceIn(
            0f,
            .5f
        ),
        animationSpec = tween(durationMillis = 100),
        label = "Scale Cancel Icon"
    )
    LaunchedEffect(isEntryCancelled) {

        if (isEntryCancelled) {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }

    Row(
        modifier = modifier
            .height(buttonSize)
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDragEnd = {
                        isLongPressed = false
                        dragOffsetX = 0f
                        onLongPressRelease(isEntryCancelled)
                        isEntryCancelled = false
                    },
                    onDrag = { change, _ ->
                        dragOffsetX += change.positionChange().x

                        // Trigger vibration when dragging a significant distance
                        isEntryCancelled = dragOffsetX < -200f
                    }
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        val density = LocalDensity.current
        val adjustmentRecordButtonOffsetX by remember {
            derivedStateOf {
                val pulsatingCircleSizePx = with(density) {
                    pulsatingCircleSize.toPx()
                }

                val buttonSizePx = with(density) { buttonSize.toPx() }
                pulsatingCircleSizePx / 2 - buttonSizePx / 2
            }
        }
        val recordButtonOffset by remember {
            derivedStateOf {
                IntOffset(
                    x = if (isLongPressed) adjustmentRecordButtonOffsetX.toInt() else 0,
                    y = 0
                )
            }
        }

        // Cancel icon
        if (isLongPressed) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .scale(cancelIconScale)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.errorContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.cancel_recording),
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }


        // Record button with animation

        Box(
            modifier = Modifier.offset { recordButtonOffset },
            contentAlignment = Alignment.Center
        ) {
            RecordButton(isRecording = isLongPressed,
                onClick = { recordAudioPermissionResultLauncher.launch(Manifest.permission.RECORD_AUDIO) },
                onLongClick = {
                    isLongPressed = true
                    recordAudioPermissionResultLauncher.launch(Manifest.permission.RECORD_AUDIO)
                },
                onLongPressRelease = {
                    isLongPressed = false
                    onLongPressRelease(isEntryCancelled)
                },
                buttonSize = buttonSize
            )

            if (isLongPressed){

                ButtonPulsatingCircle(
                    baseSize = (pulsatingCircleSize.value - 20.dp.value).dp,
                    pulseSize = pulsatingCircleSize
                )
            }
        }
    }

    if (isPermissionDialogOpen){
        PermissionDialog(
            isPermanentlyDeclined
        )
    }
}


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
                    brush = GradientScheme.PressedGradient,
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