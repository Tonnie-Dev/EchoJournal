package com.tonyxlab.echojournal.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.Topic
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.theme.EchoUltraLightGray

@Composable
fun TopicTag(
    topic: Topic,
    onClearClick: (Topic) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(MaterialTheme.spacing.spaceLarge)
            .clip(CircleShape)
            .background(
                color = EchoUltraLightGray,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.spaceTen)) {

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .5f)
                        )
                    ) {
                        append("# ")
                    }
                    append(topic.name)
                },
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Spacer(modifier = Modifier.width(MaterialTheme.spacing.spaceExtraSmall))

            Icon(
                modifier = Modifier
                    .size(MaterialTheme.spacing.spaceMedium)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onClearClick(topic)
                    },
                imageVector = Icons.Default.Clear,
                contentDescription = stringResource(id = R.string.text_delete_topic),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .3f)
            )
        }
    }


}