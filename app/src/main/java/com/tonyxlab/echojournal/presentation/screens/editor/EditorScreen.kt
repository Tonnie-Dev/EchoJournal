package com.tonyxlab.echojournal.presentation.screens.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.core.base.BaseContentLayout
import com.tonyxlab.echojournal.presentation.core.components.AppTopBar
import com.tonyxlab.echojournal.presentation.core.components.TopicDropDown
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.screens.editor.components.EditorBottomButtons
import com.tonyxlab.echojournal.presentation.screens.editor.components.EditorBottomSheet
import com.tonyxlab.echojournal.presentation.screens.editor.components.EditorTextField
import com.tonyxlab.echojournal.presentation.screens.editor.components.ExitDialog
import com.tonyxlab.echojournal.presentation.screens.editor.components.MoodChooseButton
import com.tonyxlab.echojournal.presentation.screens.editor.components.TopicTagsRow
import com.tonyxlab.echojournal.presentation.screens.editor.handling.EditorActionEvent
import com.tonyxlab.echojournal.presentation.screens.editor.handling.EditorUiEvent
import com.tonyxlab.echojournal.presentation.screens.editor.handling.EditorUiState
import com.tonyxlab.echojournal.presentation.screens.home.components.MoodPlayer
import com.tonyxlab.echojournal.utils.toInt
import timber.log.Timber

@Composable
fun EditorScreenRoot(
    echoId: Long,
    audioFilePath: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: EditorViewModel =
        hiltViewModel<EditorViewModel, EditorViewModel.EditorViewModelFactory> { factory ->
            factory.create(echoId, audioFilePath)
        }

    Timber.i("ES: $audioFilePath")

    BaseContentLayout(
        modifier = modifier,
        viewModel = viewModel,
        actionsEventHandler = { _, actionEvent ->
            when (actionEvent) {
                EditorActionEvent.NavigateBack -> navigateBack()
            }
        },
        topBar = {
            AppTopBar(
                title =
                    if (echoId < 0) {
                        stringResource(id = R.string.title_new_entry)
                    } else {
                        stringResource(id = R.string.title_edit_entry)
                    },
                onBackClick = {
                    viewModel.onEvent(EditorUiEvent.ExitDialogToggled)
                },
                isShowBackButton = true,
            )
        },
        bottomBar = { uiState ->
            val context = LocalContext.current
            EditorBottomButtons(
                modifier = Modifier.padding(MaterialTheme.spacing.spaceMedium),
                primaryButtonText = stringResource(id = R.string.button_text_save),
                onCancelClick = { viewModel.onEvent(EditorUiEvent.ExitDialogToggled) },
                onConfirmClick = {
                    val outputDir = context.filesDir
                    viewModel.onEvent(EditorUiEvent.SaveButtonClicked(outputDir!!))
                },
                primaryButtonEnabled = uiState.isSaveEnabled,
            )
        },
        onBackPressed = { viewModel.onEvent((EditorUiEvent.ExitDialogToggled)) },
        containerColor = MaterialTheme.colorScheme.surface,
    ) { uiState ->

        EditorScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent,
        )

        // EditorBottomSheet
        EditorBottomSheet(
            editorSheetState = uiState.editorSheetState,
            onEvent = viewModel::onEvent,
        )

        if (uiState.showExitDialog) {
            ExitDialog(
                headline = stringResource(id = R.string.dialog_text_cancel),
                onConfirm = { viewModel.onEvent(EditorUiEvent.ExitDialogConfirmClicked) },
                onDismissRequest = { viewModel.onEvent(EditorUiEvent.ExitDialogToggled) },
                supportingText = stringResource(id = R.string.dialog_text_leave_confirmation),
                cancelButtonText = stringResource(id = R.string.button_text_cancel),
                confirmButtonText = stringResource(id = R.string.dialog_text_exit),
            )
        }
    }
}

@Composable
fun EditorScreen(
    uiState: EditorUiState,
    onEvent: (EditorUiEvent) -> Unit,
) {
    Box {
        var topicOffset by remember { mutableStateOf(IntOffset.Zero) }

        // Will be used to calculate the Y-Axis offset of the topic Offset
        val verticalSpace = (MaterialTheme.spacing.spaceMedium).toInt()

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(vertical = MaterialTheme.spacing.spaceSmall),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceSmall),
        ) {
            EditorTextField(
                modifier = Modifier.fillMaxWidth(),
                textValue = uiState.titleValue,
                onValueChange = { onEvent(EditorUiEvent.TitleValueChanged(it)) },
                hintText = stringResource(id = R.string.text_add_title),
                leadingIcon = {
                    MoodChooseButton(
                        mood = uiState.currentMood,
                        onClick = {
                            onEvent(
                                EditorUiEvent.BottomSheetOpened(mood = uiState.currentMood),
                            )
                        },
                    )
                },
                textStyle = MaterialTheme.typography.titleMedium,
                iconSpacing = MaterialTheme.spacing.spaceDoubleDp * 3,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceTwelve),
            ) {
                MoodPlayer(
                    modifier = Modifier.height(MaterialTheme.spacing.spaceMedium * 3),
                    mood = uiState.currentMood,
                    playerState = uiState.playerState,
                    onPlayClick = { onEvent(EditorUiEvent.PlayClicked) },
                    onPauseClick = { onEvent(EditorUiEvent.PauseClicked) },
                    onResumeClick = { onEvent(EditorUiEvent.ResumeClicked) },
                )

                // TODO: Check what to do with this button
            }

            TopicTagsRow(
                modifier =
                    Modifier
                        .onGloballyPositioned { coordinates ->
                            topicOffset =
                                IntOffset(
                                    x = coordinates.positionInParent().x.toInt(),
                                    y =
                                        coordinates
                                            .positionInParent().y.toInt() +
                                            coordinates.size.height + verticalSpace,
                                )
                        }
                        .onFocusChanged {
                            onEvent(EditorUiEvent.TopicValueChanged(""))
                        },
                value = uiState.topicValue,
                onValueChange = { onEvent(EditorUiEvent.TopicValueChanged(it)) },
                topics = uiState.currentTopics,
                onTagClearClick = { onEvent(EditorUiEvent.TagClearClicked(it)) },
            )

            // Description Field

            EditorTextField(
                modifier = Modifier.fillMaxWidth(),
                textValue = uiState.descriptionValue,
                onValueChange = { onEvent(EditorUiEvent.DescriptionValueChanged(it)) },
                hintText = stringResource(id = R.string.text_add_description),
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(MaterialTheme.spacing.spaceMedium),
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(id = R.string.text_add_description),
                        tint = MaterialTheme.colorScheme.outlineVariant,
                    )
                },
                singleLine = false,
            )
        }

        TopicDropDown(
            searchQuery = uiState.topicValue,
            topics = uiState.foundTopics,
            onTopicClick = {
                onEvent(
                    EditorUiEvent.TopicSelected(it),
                )
            },
            onCreateClick = { onEvent(EditorUiEvent.CreateTopicClicked) },
            startOffset = topicOffset,
        )
    }
}