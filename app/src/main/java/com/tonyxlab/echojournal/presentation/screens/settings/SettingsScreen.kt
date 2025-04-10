package com.tonyxlab.echojournal.presentation.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.tonyxlab.echojournal.presentation.core.components.AppTopBar
import com.tonyxlab.echojournal.presentation.core.components.MoodRow
import com.tonyxlab.echojournal.presentation.core.components.TopicDropDown
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.screens.settings.components.SettingsItem
import com.tonyxlab.echojournal.presentation.screens.settings.components.TopicTagsWithAddButton
import com.tonyxlab.echojournal.presentation.screens.settings.handling.SettingsUiEvent
import com.tonyxlab.echojournal.presentation.screens.settings.handling.SettingsUiState
import com.tonyxlab.echojournal.utils.toInt

@Composable
fun SettingsScreenRoot(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
) {
    val viewModel: SettingsViewModel = hiltViewModel()

    BaseContentLayout(
        modifier = modifier.padding(top = MaterialTheme.spacing.spaceSmall),
        viewModel = viewModel,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.header_text_settings),
                isShowBackButton = true,
                onBackClick = { navigateToHome() },
            )
        },
    ) { uiState ->

        SettingsScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent,
        )
    }
}

@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onEvent: (SettingsUiEvent) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium),
    ) {
        // Mood Settings
        SettingsItem(
            title = stringResource(id = R.string.title_text_my_mood),
            description = stringResource(id = R.string.text_mood_header_desc),
        ) {
            MoodRow(
                moods = uiState.moods,
                activeMood = uiState.activeMood,
                onMoodClick = { onEvent(SettingsUiEvent.SelectMood(it)) },
            )
        }

        // Topics Settings

        Box {
            var topicOffset by remember { mutableStateOf(IntOffset.Zero) }

            // Used to calculate the y-axis offset of the topic offset

            val verticalSpace = MaterialTheme.spacing.spaceMedium.toInt()

            SettingsItem(
                title = stringResource(R.string.header_text_my_topics),
                description = stringResource(id = R.string.header_text_description),
            ) {
                TopicTagsWithAddButton(
                    modifier =
                        Modifier.onGloballyPositioned { coordinates ->

                            topicOffset =
                                IntOffset(
                                    x = coordinates.positionInParent().x.toInt(),
                                    y = coordinates.positionInParent().y.toInt() + coordinates.size.height + verticalSpace,
                                )
                        },
                    topicState = uiState.topicState,
                    onEvent = onEvent,
                )
            }

            TopicDropDown(
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.spaceMedium),
                searchQuery = uiState.topicState.topicValue,
                topics = uiState.topicState.foundTopics,
                onTopicClick = { onEvent(SettingsUiEvent.SelectTopic(it)) },
                onCreateClick = { onEvent(SettingsUiEvent.CreateTopicClick) },
                startOffset = topicOffset,
            )
        }
    }
}