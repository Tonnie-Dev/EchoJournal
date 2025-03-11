package com.tonyxlab.echojournal.presentation.screens.home.components

import android.R.attr.text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.screens.home.HomeScreenRoot
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordingBottomSheet(
    homeSheetState: HomeUiState.RecordingSheetState,
    onEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    val sheetState = rememberModalBottomSheetState()


    if (homeSheetState.isVisible) {


        ModalBottomSheet(
            onDismissRequest = { onEvent(HomeUiEvent.StopRecording(saveFile = false)) },
            sheetState = sheetState
        ) {


            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


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