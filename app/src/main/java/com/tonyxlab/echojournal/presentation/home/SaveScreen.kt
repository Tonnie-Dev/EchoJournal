package com.tonyxlab.echojournal.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.navigation.compose.hiltViewModel
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.domain.model.Mood.Excited
import com.tonyxlab.echojournal.domain.model.Mood.Neutral
import com.tonyxlab.echojournal.domain.model.Mood.Peaceful
import com.tonyxlab.echojournal.domain.model.Mood.Sad
import com.tonyxlab.echojournal.domain.model.Mood.Stressed
import com.tonyxlab.echojournal.presentation.components.AppButton
import com.tonyxlab.echojournal.presentation.components.AppIcon
import com.tonyxlab.echojournal.presentation.components.AppTopBar
import com.tonyxlab.echojournal.presentation.components.BasicEntryTextField
import com.tonyxlab.echojournal.presentation.components.PlayTrackUnit
import com.tonyxlab.echojournal.presentation.components.TopicSelector
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.Secondary70
import com.tonyxlab.echojournal.presentation.ui.theme.Secondary90
import com.tonyxlab.echojournal.presentation.ui.theme.spacing
import com.tonyxlab.echojournal.utils.TextFieldValue
import timber.log.Timber

@Composable
fun SaveScreen(
    onPresBack: () -> Unit,
    onCancelEditor: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val seekFieldValue by viewModel.seekFieldValue.collectAsState()
    val titleFieldValue by viewModel.titleFieldValue.collectAsState()
    val topicFieldValue by viewModel.topicFieldValue.collectAsState()
    val descriptionFieldValue by viewModel.descriptionFieldValue.collectAsState()

    val uiState by viewModel.uiState.collectAsState()

    SaveScreenContent(
        modifier = modifier,
        titleFieldValue = titleFieldValue,
        topicFieldValue = topicFieldValue,
        descriptionFieldValue = descriptionFieldValue,
        isPlaying = uiState.isPlaying,
        seekValue = uiState.seekValue,
        onPressBack = onPresBack,
        onSeek = {},
        echoLength = 0,
        onTogglePlay = {},
        onSaveEditor = viewModel::doSave,
        isSave = viewModel.canSave(),
        onCancelEditor = onCancelEditor,
        mood = uiState.mood,
        isShowMoodTitleIcon = uiState.isShowMoodTitleIcon,
        isMoodConfirmButtonHighlighted = uiState.isMoodConfirmButtonHighlighted,
        onShowMoodSelectionSheet = viewModel::showMoodSelectionSheet,
        isShowMoodSelectionSheet = uiState.isShowMoodSelectionSheet,
        onDismissMoodSelectionSheet = viewModel::dismissMoodSelectionModalSheet,
        onSelectMood = viewModel::setMood,
        onConfirmMoodSelection = viewModel::confirmMoodSelection,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveScreenContent(
    titleFieldValue: TextFieldValue<String>,
    topicFieldValue: TextFieldValue<String>,
    descriptionFieldValue: TextFieldValue<String>,
    isPlaying: Boolean,
    seekValue: Float,
    onSeek: (Float) -> Unit,
    echoLength: Int,
    onTogglePlay: () -> Unit,
    onCancelEditor: () -> Unit,
    onSaveEditor: () -> Unit,
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
            onPressBack = onPressBack,
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

            BasicEntryTextField(modifier = Modifier.fillMaxWidth(),
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
                        AppIcon(modifier = Modifier
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

            TopicSelector(topicFieldValue = topicFieldValue)

            BasicEntryTextField(modifier = Modifier.fillMaxWidth(),
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
                    buttonText = stringResource(id = R.string.text_cancel)
                )
                AppButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onSaveEditor,
                    isEnabled = isSave,
                    isHighlighted = isSave,
                    buttonText = stringResource(id = R.string.text_save)
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
                buttonText = stringResource(id = R.string.text_cancel),
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

        SaveScreenContent(
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
            mood = Mood.Other,
            onSelectMood = {},
            isSave = false
        )
    }
}