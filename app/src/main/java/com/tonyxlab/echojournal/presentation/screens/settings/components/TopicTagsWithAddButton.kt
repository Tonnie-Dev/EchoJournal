@file:OptIn(ExperimentalLayoutApi::class)

package com.tonyxlab.echojournal.presentation.screens.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.fastForEach
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.core.components.TopicTag
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.screens.settings.handling.SettingsUiEvent
import com.tonyxlab.echojournal.presentation.screens.settings.handling.SettingsUiState.TopicState
import com.tonyxlab.echojournal.presentation.theme.EchoUltraLightGray

@Composable
fun TopicTagsWithAddButton(
    topicState: TopicState,
    onEvent: (SettingsUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            MaterialTheme.spacing.spaceSmall
        ),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceDoubleDp * 3)
    ) {

        topicState.currentTopics.fastForEach { topic ->

            TopicTag(
                topic = topic,
                onClearClick = { onEvent(SettingsUiEvent.ClearTagClick(topic)) }
            )
        }

        if (topicState.isAddButtonVisible) {
            TopicAddButton
        }
    }


}


@Composable
fun TopicAddButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .size(MaterialTheme.spacing.spaceLarge)
            .clickable { onClick() },
        shape = CircleShape,
        color = EchoUltraLightGray,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {

        Icon(
            modifier = Modifier.padding(MaterialTheme.spacing.spaceExtraSmall),
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.text_add_topic)
        )
    }
}