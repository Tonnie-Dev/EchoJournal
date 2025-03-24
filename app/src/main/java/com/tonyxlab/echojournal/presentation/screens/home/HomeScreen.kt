package com.tonyxlab.echojournal.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.core.base.BaseContentLayout
import com.tonyxlab.echojournal.presentation.core.components.EmptyScreen
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.screens.home.components.EchoFilter
import com.tonyxlab.echojournal.presentation.screens.home.components.EchoesList
import com.tonyxlab.echojournal.presentation.screens.home.components.FilterList
import com.tonyxlab.echojournal.presentation.screens.home.components.HomeFab
import com.tonyxlab.echojournal.presentation.screens.home.components.HomeTopBar
import com.tonyxlab.echojournal.presentation.screens.home.components.RecordingBottomSheet
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeActionEvent
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiState

@Composable
fun HomeScreenRoot(
    isDataLoaded: () -> Unit,
    isLaunchedFromWidget: Boolean,
    navigateToEditorScreen: ( audioFilePath: String) -> Unit,
    navigateToSettingScreen: () -> Unit,
    modifier: Modifier = Modifier
) {

    val viewModel: HomeViewModel = hiltViewModel()

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
            HomeFab(
                onResult = { isGranted, isLongClicked ->

                    if (isGranted) {
                        if (isLongClicked) {
                            viewModel.onEvent(HomeUiEvent.ActionButtonStartRecording)
                        } else {
                            viewModel.onEvent(HomeUiEvent.StartRecording)
                        }
                    }
                },
                onLongPressRelease = { isEchoCancelled ->

                    viewModel.onEvent(HomeUiEvent.ActionButtonStopRecording(saveFile = !isEchoCancelled))
                }
            )
        },
        actionsEventHandler = { _, actionEvent ->
            when (actionEvent) {
                is HomeActionEvent.NavigateToEditorScreen -> {
                    navigateToEditorScreen(actionEvent.audioFilePath)
                }

                is HomeActionEvent.DataLoaded -> {
                    isDataLoaded()
                    if (isLaunchedFromWidget) {
                        viewModel.onEvent(HomeUiEvent.StartRecording)
                    }
                }

            }


        }) { uiState ->

        if (uiState.echoes.isEmpty() && !uiState.isFilterActive) {
            EmptyScreen(modifier = Modifier.padding(MaterialTheme.spacing.spaceMedium))
        } else {
            HomeScreen(
                uiState = uiState,
                onEvent = viewModel::onEvent

            )
        }


        RecordingBottomSheet(
            homeSheetState = uiState.recordingSheetState,
            onEvent = viewModel::onEvent
        )

    }

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


