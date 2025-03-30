package com.tonyxlab.echojournal.presentation.screens.editor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.navigation.compose.hiltViewModel
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.domain.model.Mood.Excited
import com.tonyxlab.echojournal.domain.model.Mood.Neutral
import com.tonyxlab.echojournal.domain.model.Mood.Peaceful
import com.tonyxlab.echojournal.domain.model.Mood.Sad
import com.tonyxlab.echojournal.domain.model.Mood.Stressed
import com.tonyxlab.echojournal.presentation.core.base.BaseContentLayout
import com.tonyxlab.echojournal.presentation.core.components.AppButton
import com.tonyxlab.echojournal.presentation.core.components.AppIcon
import com.tonyxlab.echojournal.presentation.core.components.AppTopBar
import com.tonyxlab.echojournal.presentation.core.components.BasicEntryTextField
import com.tonyxlab.echojournal.presentation.core.components.PlayTrackUnit
import com.tonyxlab.echojournal.presentation.core.components.TopicDropDown
import com.tonyxlab.echojournal.presentation.core.components.TopicSelector
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
import com.tonyxlab.echojournal.presentation.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.theme.Secondary70
import com.tonyxlab.echojournal.presentation.theme.Secondary90
import com.tonyxlab.echojournal.utils.TextFieldValue
import com.tonyxlab.echojournal.utils.toInt

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
                title = if (echoId < 0)
                    stringResource(id = R.string.title_new_entry)
                else
                    stringResource(id = R.string.title_edit_entry), onBackClick = {
                    viewModel.onEvent(EditorUiEvent.ExitDialogToggled)
                }
            )
        },
        bottomBar = { uiState ->
            val context = LocalContext.current
            EditorBottomButtons(
                primaryButtonText = stringResource(id = R.string.button_text_save),
                // TODO: Review This Event
                onCancelClick = { viewModel.onEvent(EditorUiEvent.ExitDialogToggled) },
                onConfirmClick = {

                    val outputDir = context.filesDir
                    viewModel.onEvent(EditorUiEvent.SaveButtonClicked(outputDir!!))
                }
            )
        }, onBackPressed = { viewModel.onEvent((EditorUiEvent.ExitDialogToggled)) },
        containerColor = MaterialTheme.colorScheme.surface

    ) { uiState ->

        EditorScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent
        )

       // EditorBottomSheet

        EditorBottomSheet(
            editorSheetState = uiState.editorSheetState,
            onEvent = viewModel::onEvent
        )

        if (uiState.showExitDialog) {
            ExitDialog(
                headline = stringResource(id = R.string.dialog_text_cancel),
                onConfirm = { viewModel.onEvent(EditorUiEvent.ExitDialogConfirmClicked) },
                onDismissRequest = { viewModel.onEvent(EditorUiEvent.ExitDialogToggled) },
                supportingText = stringResource(id = R.string.dialog_text_leave_confirmation),
                cancelButtonText = stringResource(id = R.string.button_text_cancel),
                confirmButtonText = stringResource(id = R.string.dialog_text_exit)
            )
        }

    }
}

@Composable
fun EditorScreen(
    uiState: EditorUiState,
    onEvent: (EditorUiEvent) -> Unit
) {
    Box {
        var topicOffset by remember { mutableStateOf(IntOffset.Zero) }

        // Will be used to calculate the Y-Axis offset of the topic Offset
        val verticalSpace = (MaterialTheme.spacing.spaceMedium).toInt()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = MaterialTheme.spacing.spaceSmall),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceSmall)
        )
        {
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
                                EditorUiEvent.BottomSheetOpened(mood = uiState.currentMood)
                            )
                        }
                    )

                },
                textStyle = MaterialTheme.typography.titleMedium,
                iconSpacing = MaterialTheme.spacing.spaceDoubleDp * 3
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceTwelve)
            ) {

                MoodPlayer(
                    modifier = Modifier.height(MaterialTheme.spacing.spaceExtraSmall),
                    mood = uiState.currentMood,
                    playerState = uiState.playerState,
                    onPlayClick = { onEvent(EditorUiEvent.PlayClicked) },
                    onPauseClick = { onEvent(EditorUiEvent.PauseClicked) },
                    onResumeClick = { onEvent(EditorUiEvent.ResumeClicked) }
                )

                // TODO: Check what to do with this button
            }


            TopicTagsRow(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        topicOffset = IntOffset(
                            x = coordinates.positionInParent().x.toInt(),
                            y = coordinates
                                .positionInParent().y.toInt() +
                                    coordinates.size.height + verticalSpace
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
                        tint = MaterialTheme.colorScheme.outlineVariant
                    )
                },
                singleLine = false
            )

        }

        TopicDropDown(
            searchQuery = uiState.topicValue,
            topics = uiState.foundTopics,
            onTopicClick = {
                onEvent(
                    EditorUiEvent.TopicSelected(it)
                )
            },
            onCreateClick = { onEvent(EditorUiEvent.CreateTopicClicked) },
            startOffset = topicOffset
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreenContent(
    titleFieldValue: TextFieldValue<String>,
    topicFieldValue: TextFieldValue<String>,
    descriptionFieldValue: TextFieldValue<String>,
    savedTopics: List<String>,
    selectedTopics: List<String>,
    isPlaying: Boolean,
    seekValue: Float,
    onSeek: (Float) -> Unit,
    echoLength: Int,
    onTogglePlay: () -> Unit,
    onCancelEditor: () -> Unit,
    onSaveEditor: () -> Unit,
    onSavedTopicsChange: (List<String>) -> Unit,
    onCurrentSelectedTopicsChange: (List<String>) -> Unit,
    isSave: Boolean,
    mood: Mood,
    isShowMoodTitleIcon: Boolean,
    isShowMoodSelectionSheet: Boolean,
    onShowMoodSelectionSheet: () -> Unit,
    onSelectMood: (Mood) -> Unit,
    onConfirmMoodSelection: () -> Unit,
    isMoodConfirmButtonHighlighted: Boolean,
    onDismissMoodSelectionSheet: () -> Unit,
    onPressBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier, topBar = {

        AppTopBar(
            title = stringResource(R.string.title_new_entry),
            style = MaterialTheme.typography.headlineMedium,
            onBackClick = onPressBack,
            isShowBackButton = true
        )

    }) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    horizontal = MaterialTheme.spacing.spaceMedium,
                    vertical = MaterialTheme.spacing.spaceSmall
                ),

            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {

            BasicEntryTextField(
                modifier = Modifier.fillMaxWidth(),
                textFieldValue = titleFieldValue,
                hint = stringResource(id = R.string.text_add_title),
                isHeadline = true,
                gap = MaterialTheme.spacing.spaceDoubleDp * 3,
                leadingContent = {

                    if (isShowMoodTitleIcon) {
                        Image(
                            modifier = Modifier
                                .size(MaterialTheme.spacing.spaceLarge)
                                .clickable {

                                    onShowMoodSelectionSheet()
                                },
                            painter = painterResource(mood.icon),
                            contentDescription = mood.name
                        )
                    } else
                        AppIcon(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Secondary90)
                                .size(MaterialTheme.spacing.spaceLarge)
                                .clickable {
                                    onShowMoodSelectionSheet()
                                }
                                .padding(MaterialTheme.spacing.spaceDoubleDp * 3),
                            imageVector = Icons.Default.Add,
                            tint = Secondary70)
                })

            PlayTrackUnit(
                isPlaying = isPlaying,
                seekValue = seekValue,
                onSeek = onSeek,
                echoLength = echoLength,
                onTogglePlay = onTogglePlay
            )

            TopicSelector(
                topicFieldValue = topicFieldValue,
                savedTopics = savedTopics,
                currentSelectedTopics = selectedTopics,
                onCurrentSelectedTopicsChange = onCurrentSelectedTopicsChange,
                onSavedTopicsChange = onSavedTopicsChange
            )

            BasicEntryTextField(
                modifier = Modifier.fillMaxWidth(),
                textFieldValue = descriptionFieldValue,
                hint = stringResource(id = R.string.text_add_description),
                isHeadline = false,
                gap = MaterialTheme.spacing.spaceDoubleDp * 3,
                singleLine = false,
                leadingContent = {
                    AppIcon(
                        modifier = Modifier
                            .size(
                                width = MaterialTheme.spacing.spaceMedium,
                                height = MaterialTheme.spacing.spaceTen * 2
                            )
                            .padding(MaterialTheme.spacing.spaceDoubleDp * 3),
                        imageVector = Icons.Default.Edit,
                        tint = Secondary70
                    )
                })

            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
            ) {

//Other Buttons
                AppButton(
                    onClick = onCancelEditor,
                    buttonText = stringResource(id = R.string.dialog_text_cancel)
                )
                AppButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onSaveEditor,
                    isEnabled = isSave,
                    isHighlighted = isSave,
                    buttonText = stringResource(id = R.string.button_text_save)
                )
            }
            if (isShowMoodSelectionSheet) {

                ModalBottomSheet(
                    onDismissRequest = onDismissMoodSelectionSheet,
                    content = {
                        MoodSelectionSheetContent(
                            onSelectMood = onSelectMood,
                            onConfirmMoodSelection = {
                                onConfirmMoodSelection()
                                onDismissMoodSelectionSheet()

                            },
                            onCancelMoodSelection = {
                                onDismissMoodSelectionSheet()
                            },
                            isMoodConfirmButtonHighlighted = isMoodConfirmButtonHighlighted
                        )

                    })

            }

        }


    }

}

@Composable
fun MoodSelectionSheetContent(
    onSelectMood: (Mood) -> Unit,
    onConfirmMoodSelection: () -> Unit,
    onCancelMoodSelection: () -> Unit,
    isMoodConfirmButtonHighlighted: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.spaceMedium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.text_how_are_you_doing),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceLarge))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.spaceSmall)
        ) {
            var selectedIndex by remember { mutableIntStateOf(-1) }

            val moods = listOf(Stressed, Sad, Neutral, Peaceful, Excited)

            moods.fastForEachIndexed { i, mood ->

                val isSelected = selectedIndex == i

                MoodItem(
                    modifier = Modifier.weight(1f),
                    mood = mood,
                    selected = isSelected,
                    onSelectMoodItem = {
                        selectedIndex = i
                        onSelectMood(it)

                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceExtraSmall * 3))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {

            //Sheet Buttons
            AppButton(
                buttonText = stringResource(id = R.string.dialog_text_cancel),
                onClick = onCancelMoodSelection
            )
            AppButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onConfirmMoodSelection()
                },
                isHighlighted = isMoodConfirmButtonHighlighted,
                buttonText = stringResource(id = R.string.text_confirm),
                leadingIcon = true
            )
        }


    }
}

@Composable
private fun MoodItem(
    mood: Mood,
    selected: Boolean,
    onSelectMoodItem: (Mood) -> Unit,
    modifier: Modifier = Modifier
) {


    Column(
        modifier = modifier

            .clickable { onSelectMoodItem(mood) }
            .padding(MaterialTheme.spacing.spaceExtraSmall),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceSmall),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = if (selected)
                painterResource(mood.icon)
            else
                painterResource(mood.outlinedIcon),
            contentDescription = mood.name
        )
        Text(
            text = mood.name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
    }

}

@PreviewLightDark
@Composable
private fun SaveScreenPreview() {
    EchoJournalTheme {

        EditorScreenContent(
            titleFieldValue = TextFieldValue("Title"),
            topicFieldValue = TextFieldValue("Topic"),
            descriptionFieldValue = TextFieldValue("Description"),
            isPlaying = false,
            seekValue = 0.0f,
            onSeek = {},
            echoLength = 0,
            onTogglePlay = {},
            onShowMoodSelectionSheet = {},
            isShowMoodSelectionSheet = false,
            isShowMoodTitleIcon = false,
            isMoodConfirmButtonHighlighted = false,
            onDismissMoodSelectionSheet = {},
            onPressBack = {},
            onConfirmMoodSelection = {},
            onSaveEditor = {},
            onCancelEditor = {},
            mood = Mood.Undefined,
            onSelectMood = {},
            selectedTopics = emptyList(),
            savedTopics = emptyList(),
            isSave = false,
            onSavedTopicsChange = {},
            onCurrentSelectedTopicsChange = {}
        )
    }
}