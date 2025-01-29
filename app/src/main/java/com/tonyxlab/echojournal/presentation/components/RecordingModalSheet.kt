package com.tonyxlab.echojournal.presentation.components

import android.Manifest
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.LocalSpacing
import com.tonyxlab.echojournal.presentation.ui.theme.Primary90
import com.tonyxlab.echojournal.presentation.ui.theme.Primary95
import com.tonyxlab.echojournal.presentation.ui.theme.gradient
import com.tonyxlab.echojournal.presentation.ui.theme.spacing
import androidx.compose.runtime.rememberUpdatedState


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun RecordingModalSheet(
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    onPauseRecording:() -> Unit,
    onCancelRecording:() -> Unit,
    onDismissRecordingModalSheet: () -> Unit,
    recordingTime: String,
    modifier: Modifier = Modifier
) {

    val recordPermission = rememberPermissionState(Manifest.permission.RECORD_AUDIO)


    LaunchedEffect(recordPermission) {
        val permissionResult = recordPermission.status
        if (permissionResult.isGranted.not()) {
            if (permissionResult.shouldShowRationale) {
                // TODO Show Alert to Grant Permission
            } else {
                recordPermission.launchPermissionRequest()
            }
        }
     
    }

    ModalBottomSheet(
        modifier = modifier,
        content = {

            RecordingModalSheetContent(
                // isRecordingInProgress = isRecordingInProgress,
                onStartRecording = onStartRecording,
                onStopRecording = onStopRecording,
                onPauseRecording = onPauseRecording,
                onCancelRecording = onCancelRecording,
                modifier = Modifier,
                recordingTime = recordingTime
            )
        },
        onDismissRequest = onDismissRecordingModalSheet
    )
}


@Composable
private fun RecordingModalSheetContent(
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    onPauseRecording: () -> Unit,
    onCancelRecording:  () -> Unit,
    modifier: Modifier = Modifier,
    recordingTime: String = "01:13:40"
) {

    val spacing = LocalSpacing.current

    var isRecordingActivated by remember { mutableStateOf(false) }
    var isRecordingInProgress by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.spaceFifty)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val displayMessage = when {
                isRecordingInProgress -> stringResource(id = R.string.text_recording_memories)
                !isRecordingActivated -> stringResource(id = R.string.text_start_recording)
                else -> stringResource(id = R.string.text_recording_paused)
            }

            Text(
                text = displayMessage,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = recordingTime,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

        }

        if (isRecordingActivated or isRecordingInProgress) {

            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(top = MaterialTheme.spacing.spaceExtraLarge)
            ) {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .size(MaterialTheme.spacing.spaceSmall * 6)
                        .clickable { onCancelRecording() }
                        .padding(MaterialTheme.spacing.spaceSmall),
                    imageVector = Icons.Default.Close,
                    tint = MaterialTheme.colorScheme.onErrorContainer,
                    contentDescription = stringResource(R.string.icon_close_text),
                )
            }
        }


        RecordingButton(
            isRecording = isRecordingInProgress,
            onClick = {

                isRecordingInProgress = !isRecordingInProgress

                if (isRecordingInProgress) {

                    onStopRecording()
                } else {
                    isRecordingActivated = true
                    onStartRecording()
                }

            },
            modifier = Modifier
                .align(Alignment.Center)
                .padding(
                    top = MaterialTheme.spacing.spaceExtraLarge,
                    bottom = MaterialTheme.spacing.spaceMedium
                )
        )


        if (isRecordingActivated or isRecordingInProgress) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(top = MaterialTheme.spacing.spaceExtraLarge)
            ) {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onPrimaryContainer)
                        .size(spacing.spaceSmall * 6)
                        .clickable {

                            isRecordingInProgress = false
                            onPauseRecording()
                        }
                        .padding(spacing.spaceSmall),
                    imageVector = if (isRecordingInProgress) Icons.Default.Pause else Icons.Default.Done,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = stringResource(R.string.icon_close_text),
                )
            }

        }

    }


}

@Composable
private fun RecordingButton(
    isRecording: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gradient = MaterialTheme.gradient.buttonDefaultGradient

    val infiniteTransition = rememberInfiniteTransition()

    val innerCircleScaling by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.2f, animationSpec = infiniteRepeatable(
            animation = tween(1000), repeatMode = RepeatMode.Reverse
        )
    )
    val outerCircleScaling by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.3f, animationSpec = infiniteRepeatable(
            animation = tween(1000), repeatMode = RepeatMode.Reverse
        )
    )

    val fabCircleScaling by infiniteTransition.animateFloat(

        initialValue = 1f, targetValue = 1.1f, animationSpec = infiniteRepeatable(
            animation = tween(1000), repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = modifier) {
        Canvas(modifier = Modifier
            .size(MaterialTheme.spacing.spaceOneTwentyEight)
            .clickable { onClick() }) {
            if (isRecording) {
                // Outer Circle
                drawCircle(
                    color = Primary95,
                    radius = (size.minDimension.div(2)) * outerCircleScaling,
                )

                //  Inner Circle
                drawCircle(
                    color = Primary90,
                    radius = (size.minDimension.div(2.5f)) * innerCircleScaling,
                )
            }

            // FAB
            drawCircle(
                brush = gradient,
                radius = if (isRecording)
                    size.minDimension.div(3) * fabCircleScaling
                else
                    size.minDimension.div(3)
            )
        }

        // Icon
        Box(
            modifier = Modifier
                .size(MaterialTheme.spacing.spaceLarge)
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isRecording) Icons.Default.Done else Icons.Default.Mic,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun RecordingBottomSheetPreview() {
    EchoJournalTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .fillMaxSize()
                .padding(vertical = MaterialTheme.spacing.spaceFifty),
        ) {
            RecordingModalSheet(
                onStartRecording = {},
                onStopRecording = {},
                onPauseRecording = {},
                onCancelRecording = {},
                onDismissRecordingModalSheet = {},
                recordingTime = "02:18:01"
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun BottomSheetContentPreview() {

    EchoJournalTheme {

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .fillMaxSize()
                .padding(vertical = MaterialTheme.spacing.spaceFifty),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceExtraLarge)
        ) {


            RecordingModalSheetContent(
                onStartRecording = {},
                onStopRecording = {},
                onPauseRecording = {},
                onCancelRecording = {},
            )

            RecordingModalSheetContent(
                onStartRecording = {},
                onStopRecording = {},
                onPauseRecording = {},
                onCancelRecording = {},
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun BottomSheetLayoutPreview() {

    EchoJournalTheme {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(vertical = MaterialTheme.spacing.spaceTwoHundred),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {
            RecordingButton(isRecording = true, onClick = {})
            RecordingButton(isRecording = false, onClick = {})
        }
    }

}