package com.tonyxlab.echojournal.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.theme.Primary95
import com.tonyxlab.echojournal.presentation.theme.buttonSmallTextStyle
import com.tonyxlab.echojournal.presentation.core.utils.spacing

@Composable
fun TopicChip(
    topic: String,
    modifier: Modifier = Modifier,
    hasTrailingIcon: Boolean = false,
    onDeleteTopic: (() -> Unit)? = null
) {

    FilterChip(modifier = modifier,
        selected = true,
        onClick = {},
        shape = MaterialTheme.shapes.large,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Primary95
        ),
        leadingIcon = {
            Text("#", style = buttonSmallTextStyle)
        },

        trailingIcon = {

            if (hasTrailingIcon) {

                AppIcon(
                    modifier = Modifier
                        .size(MaterialTheme.spacing.spaceMedium)
                        .clickable { onDeleteTopic?.invoke() },
                    imageVector = Icons.Outlined.Close,
                    contentDescription = stringResource(R.string.button_text_cancel),
                    tint = MaterialTheme.colorScheme.inverseOnSurface
                )
            }

        },

        label = {

            Text(
                text = topic,
                style = buttonSmallTextStyle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        })
}


@PreviewLightDark
@Composable
private fun TopicPreview() {
    EchoJournalTheme {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            TopicChip(topic = "Topic 1", hasTrailingIcon = true)
            TopicChip(topic = "Topic 2", hasTrailingIcon = false)
            TopicChip(topic = "Topic 3", hasTrailingIcon = true)
            TopicChip(topic = "Topic 4", hasTrailingIcon = false)
            TopicChip(topic = "Topic 5", hasTrailingIcon = true)
        }
    }
}
