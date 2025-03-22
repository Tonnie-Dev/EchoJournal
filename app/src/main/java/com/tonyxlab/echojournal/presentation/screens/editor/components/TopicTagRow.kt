@file:OptIn(ExperimentalLayoutApi::class)

package com.tonyxlab.echojournal.presentation.screens.editor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tonyxlab.echojournal.domain.model.Topic
import com.tonyxlab.echojournal.presentation.core.components.TopicTag
import com.tonyxlab.echojournal.presentation.core.components.TopicTextField
import com.tonyxlab.echojournal.presentation.core.utils.spacing

@Composable
fun TopicTagsRow(
    value: String,
    onValueChange: (String) -> Unit,
    topics: List<Topic>,
    onTagClearClick: (Topic) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceSmall),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceDoubleDp * 3)
    ) {
        topics.forEach { topic ->

            TopicTag(
                topic = topic,
                onClearClick = onTagClearClick
            )
        }

        TopicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f)
        )
    }
}

