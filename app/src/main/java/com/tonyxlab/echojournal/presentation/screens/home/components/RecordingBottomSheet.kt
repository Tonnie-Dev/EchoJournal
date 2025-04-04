package com.tonyxlab.echojournal.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.Dp
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.core.utils.GradientScheme
import com.tonyxlab.echojournal.presentation.core.utils.gradient
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiState
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordingBottomSheet(
    homeSheetState: HomeUiState.RecordingSheetState,
    onEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {


    if (homeSheetState.isVisible) {

        ModalBottomSheet(
            onDismissRequest = { onEvent(HomeUiEvent.StopRecording(saveFile = false)) },

            ) {

            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                BottomSheetHeader(
                    modifier = Modifier.padding(vertical = MaterialTheme.spacing.spaceSmall),
                    isRecording = homeSheetState.isRecording,
                    recordingTime = homeSheetState.recordingTime
                )

                RecordButtons(
                    modifier = Modifier.padding(
                        vertical = MaterialTheme.spacing.spaceDoubleDp * 21,
                        horizontal = MaterialTheme.spacing.spaceMedium
                    ),
                    isRecording = homeSheetState.isRecording,
                    onCancelClick = { onEvent(HomeUiEvent.StopRecording(saveFile = false)) },
                    onRecordClick = {
                        if (homeSheetState.isRecording)
                            onEvent(HomeUiEvent.StopRecording(saveFile = true))
                        else
                            onEvent(HomeUiEvent.ResumeRecording)
                    },
                    onPauseClick = {

                        if (homeSheetState.isRecording)
                            onEvent(HomeUiEvent.PauseRecording)
                        else
                            onEvent(HomeUiEvent.StopRecording(saveFile = true))
                    }
                )

            }
        }
    }

}

@Composable
private fun BottomSheetHeader(
    recordingTime: String,
    isRecording: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceSmall)
    ) {

        // Title
        Text(
            text = if (isRecording)
                stringResource(id = R.string.text_recording_memories)
            else
                stringResource(id = R.string.text_recording_paused),
            style = MaterialTheme.typography.titleSmall
        )

        // Timer
        Box(modifier = Modifier.width(IntrinsicSize.Max)) {

            Text(
                text = if (recordingTime.length > 5)
                    recordingTime
                else
                    "00:$recordingTime"
            )

            // Hidden PlaceHolder Text [ Color is Transparent ] to define Intrinsic max width

            Text(
                text = "00:00:00",
                style = MaterialTheme.typography.labelMedium.copy(color = Color.Transparent)
            )
        }
    }
}


@Composable
fun RecordButtons(
    isRecording: Boolean,
    onCancelClick: () -> Unit,
    onRecordClick: () -> Unit,
    onPauseClick: () -> Unit,
    modifier: Modifier = Modifier,
    recordButtonSize: Dp = MaterialTheme.spacing.spaceTwelve * 6,
    auxiliaryButtonSize: Dp = MaterialTheme.spacing.spaceTwelve * 4
) {


    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(recordButtonSize),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        // Cancel Button
        IconButton(
            modifier = Modifier.size(auxiliaryButtonSize),
            onClick = onCancelClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = stringResource(id = R.string.recording_button)
            )
        }

        // Record Button
        Box(
            modifier = Modifier.size(recordButtonSize),
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .size(recordButtonSize)
                    .clip(CircleShape)
                    .background(
                        brush = GradientScheme.PrimaryGradient,
                        shape = CircleShape
                    )
                    .clickable { onRecordClick() },
                contentAlignment = Alignment.Center

            ) {

                Image(
                    painter = if (isRecording)
                        painterResource(R.drawable.ic_checkmark)
                    else
                        painterResource(R.drawable.ic_recording),
                    contentDescription = stringResource(id = R.string.recording_button)
                )


            }
            if (isRecording) {

                PulsatingButton()
            }
        }

        // Pause Button
        IconButton(
            modifier = Modifier.size(auxiliaryButtonSize),
            onClick = onPauseClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                contentColor = MaterialTheme.colorScheme.primary
            )

        ) {
            Icon(
                painter = if (isRecording)
                    painterResource(R.drawable.ic_pause)
                else
                    painterResource(R.drawable.ic_checkmark_primary),
                contentDescription = stringResource(id = R.string.pause_text)
            )
        }

    }

}