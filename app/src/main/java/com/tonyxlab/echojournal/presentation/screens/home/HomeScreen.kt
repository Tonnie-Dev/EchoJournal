package com.tonyxlab.echojournal.presentation.screens.home

import android.net.Uri
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.presentation.core.base.BaseContentLayout
import com.tonyxlab.echojournal.presentation.core.components.AppTopBar
import com.tonyxlab.echojournal.presentation.core.components.EchoCard
import com.tonyxlab.echojournal.presentation.core.components.EmptyScreen
import com.tonyxlab.echojournal.presentation.core.components.RecordingModalSheet
import com.tonyxlab.echojournal.presentation.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.core.utils.LocalSpacing
import com.tonyxlab.echojournal.presentation.screens.home.components.EchoFilter
import com.tonyxlab.echojournal.presentation.screens.home.components.EchoListPosition
import com.tonyxlab.echojournal.presentation.screens.home.components.EchoesList
import com.tonyxlab.echojournal.presentation.screens.home.components.FilterList
import com.tonyxlab.echojournal.presentation.screens.home.components.HomeTopBar
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiState
import com.tonyxlab.echojournal.utils.generateRandomEchoItems

@Composable
fun HomeScreenRoot(
    isDataLoaded: () -> Unit,
    isLaunchedFromWidget: Boolean,
     navigateToSettingScreen:()-> Unit
    modifier: Modifier = Modifier
) {

    val viewModel:HomeViewModel = hiltViewModel()

    BaseContentLayout(
        modifier = modifier,
        viewModel = viewModel,
        topBar = {

            HomeTopBar(
                title = stringResource(id = R.string.your_echo_journal),
                onSettingsClick = navigateToSettingScreen
            )
        },
        floatingActionButton = {

        }

    ) { }
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit
) {

    var filterOffset by remember { mutableStateOf(IntOffset.Zero) }
    Column {

        EchoFilter(
            filterState = uiState.filterState,
            onEvent = onEvent,
            modifier = Modifier.onGloballyPositioned { coordinates ->
                filterOffset = IntOffset(
                    x = coordinates.positionInParent().x.toInt(),
                    y = coordinates.positionInParent().y.toInt() + coordinates.size.height
                )
            }

        )

        if (uiState.echoes.isEmpty() && uiState.isFilterActive) {
            EmptyScreen(
                supportingText = stringResource(id = R.string.text_no_entries)
            )

        }
        EchoesList(
            echoes = uiState.echoes,
            onEvent = onEvent
        )

    }

    if (uiState.filterState.isMoodFilterOpen) {

        FilterList(
            filterItems = uiState.filterState.moodFilterItems,
            onItemClick = { onEvent(HomeUiEvent.ActivateMoodFilter) },
            onDismissClicked = { onEvent(HomeUiEvent.CancelMoodFilter) },
            startOffset = filterOffset
        )


    }

    if (uiState.filterState.isTopicFilterOpen) {
        FilterList(
            filterItems = uiState.filterState.topicFilterItems,
            onItemClick = { onEvent(HomeUiEvent.ActivateMoodFilter) },
            onDismissClicked = { onEvent(HomeUiEvent.CancelMoodFilter) },
            startOffset = filterOffset
        )
    }
}


@Composable
fun HomeScreen(
    onClickEcho: (String) -> Unit,
    navigateToSaveScreen: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val homeState by viewModel.homeUiState.collectAsState()

    HomeScreenContent(
        echoes = homeState.echoes,
        onClickEcho = onClickEcho,
        isPlaying = homeState.isPlaying,
        seekValue = homeState.seekValue,
        onCreateEcho = viewModel::createEcho,
        onSeek = viewModel::setSeek,
        onPlay = viewModel::play,
        onStop = viewModel::stop,
        isRecordingActivated = homeState.isRecordingActivated,
        onStartRecording = viewModel::startRecording,
        onStopRecording = {
            viewModel.dismissRecordingModalSheet()
            viewModel.stopRecording()
            navigateToSaveScreen()
        },
        onDismissRecordingModalSheet = viewModel::dismissRecordingModalSheet,
        onCancelRecording = viewModel::dismissRecordingModalSheet,
        onPauseRecording = viewModel::pauseRecording,
        recordingTime = "02:02:02"

    )
}

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
                    onClickEcho = { onClickEcho(echo.id) },
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
            onStartRecording = {},
            onStopRecording = {},
            onDismissRecordingModalSheet = {},
            onPauseRecording = {},
            onCancelRecording = {},
            recordingTime = "02:02:02"
        )

    }
}