package com.tonyxlab.echojournal.presentation.home

import android.Manifest
import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.presentation.components.AppTopBar
import com.tonyxlab.echojournal.presentation.components.EchoCard
import com.tonyxlab.echojournal.presentation.components.EmptyScreen
import com.tonyxlab.echojournal.presentation.components.RecordingModalSheet
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.LocalSpacing
import com.tonyxlab.echojournal.utils.RequestRecordPermission
import com.tonyxlab.echojournal.utils.generateRandomEchoItems

@Composable
fun HomeScreen(
    onClickEcho: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val echoes by viewModel.echoes.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val isRecordingActivated = uiState.isRecordingActivated

    HomeScreenContent(
        echoes = echoes,
        onClickEcho = onClickEcho,
        isPlaying = uiState.isPlaying,
        seekValue = uiState.seekValue,
        onCreateEcho = viewModel::onCreateEcho,
        onSeek = viewModel::onSeek,
        onPlay = viewModel::play,
        onStop = viewModel::stop,
        isRecordingActivated = uiState.isRecordingActivated,
        isRecordingInProgress = uiState.isRecordingInProgress,
        onStartRecording = viewModel::startRecording,
        onStopRecording = viewModel::stopRecording,
        onDismissRecordingModalSheet = viewModel::dismissRecordingModalSheet,
        onCancelRecording = viewModel::dismissRecordingModalSheet,
        onPauseRecording = viewModel::pauseRecording,
        recordingTime = "02:02:02"

    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreenContent(
    echoes: List<Echo>,
    onCreateEcho: () -> Unit,
    onClickEcho: (String) -> Unit,
    onPlay: (Uri) -> Unit,
    onStop: () -> Unit,
    onSeek: (Float) -> Unit,
    isPlaying: Boolean,
    seekValue: Float,
    isRecordingActivated: Boolean,
    isRecordingInProgress: Boolean,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    onPauseRecording: () -> Unit,
    onCancelRecording: () -> Unit,
    onDismissRecordingModalSheet: () -> Unit,
    recordingTime: String,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current

    var currentPlayingIndex by remember { mutableIntStateOf(-1) }


    var selectedIndex by remember { mutableIntStateOf(-1) }

    Scaffold(
        modifier = modifier,
        topBar = {

            AppTopBar(title = stringResource(id = R.string.title_text))
        },
        floatingActionButton = {

            FloatingActionButton(
                modifier = Modifier.size(spacing.spaceSmall * 8),
                onClick = onCreateEcho,
                shape = CircleShape
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_text),
                    tint = MaterialTheme.colorScheme.onPrimary
                )

            }
        }) { paddingValues ->


        echoes.ifEmpty {

            EmptyScreen(modifier = Modifier.padding(paddingValues = paddingValues))
        }

        if (isRecordingActivated) {
            RecordingModalSheet(
                onStartRecording = onStartRecording,
                onStopRecording = onStopRecording,
                onDismissRecordingModalSheet = onDismissRecordingModalSheet,
                recordingTime = recordingTime,
                onPauseRecording = onPauseRecording,
                onCancelRecording = onCancelRecording
            )
        }

        LazyColumn(contentPadding = paddingValues) {

            itemsIndexed(items = echoes, key = { _, echo -> echo.id }) { i, echo ->
                val isSelected = selectedIndex == i


                EchoCard(
                    modifier = modifier.padding(spacing.spaceMedium),
                    echo = echo,
                    isPlaying = isPlaying,
                    seekValue = seekValue,
                    onSeek = onSeek,
                    onClickEcho = onClickEcho,
                    onPlayPause = {
                        selectedIndex = i

                        if (currentPlayingIndex == i && isPlaying) {
                            onStop()
                            currentPlayingIndex = -1
                        } else {

                            if (isPlaying) {
                                onStop()
                            }

                            onPlay(echo.uri)
                            currentPlayingIndex = i
                            //onChangeRingtone(ringtone)
                        }


                    }

                )

            }

        }
    }


}


@PreviewLightDark
@Composable
private fun HomeScreenContentPreview() {

    EchoJournalTheme {

        HomeScreenContent(
            echoes = generateRandomEchoItems(),
            onCreateEcho = {},
            onClickEcho = {},
            onPlay = {},
            onStop = {},
            onSeek = {},
            isPlaying = false,
            seekValue = 4.5f,
            isRecordingActivated = false,
            isRecordingInProgress = false,
            onStartRecording = {},
            onStopRecording = {},
            onDismissRecordingModalSheet = {},
            onPauseRecording = {},
            onCancelRecording = {},
            recordingTime = "02:02:02"
        )

    }
}