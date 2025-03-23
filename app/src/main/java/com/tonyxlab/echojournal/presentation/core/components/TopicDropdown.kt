package com.tonyxlab.echojournal.presentation.core.components

import android.R.attr.onClick
import android.R.attr.startOffset
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.Topic
import com.tonyxlab.echojournal.presentation.core.utils.spacing

@Composable
fun TopicDropDown(
    searchQuery: String,
    topics: List<Topic>,
    onTopicClick: (Topic) -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier,
    startOffset: IntOffset = IntOffset.Zero
) {

    var isVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(searchQuery) {

        isVisible = searchQuery.isNotEmpty()
    }

    if (isVisible) {
        Box(
            modifier = modifier
                .offset { startOffset }
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { isVisible = isVisible.not() })
        {
            Surface(
                shape = RoundedCornerShape(MaterialTheme.spacing.spaceTen),
                shadowElevation = MaterialTheme.spacing.spaceTen
            ) {

                LazyColumn(modifier = Modifier.padding(MaterialTheme.spacing.spaceExtraSmall)) {

                    items(items = topics) { topic ->
                        TopicItem(
                            topic.name,
                            onClick = {
                                onTopicClick(topic)
                                focusManager.clearFocus()
                            }
                        )
                    }

                    item {
                        CreateButton(
                            searchQuery = searchQuery,
                            onClick = {
                                onCreateClick()
                                isVisible = isVisible.not()
                                focusManager.clearFocus()
                            }
                        )

                    }
                }
            }

        }
    }

}

// TODO: Try to build this with annotated string builder
@Composable
fun TopicItem(
    topic: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(MaterialTheme.spacing.spaceDoubleDp * 3))
            .clickable() { onClick() }
            .padding(MaterialTheme.spacing.spaceTwelve)
    ) {
        Text(
            text = "#",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary.copy(alpha = .5f)
            )
        )

        // Topic

        Text(
            text = topic,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun CreateButton(
    searchQuery: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(MaterialTheme.spacing.spaceDoubleDp * 3))
            .clickable { onClick() }
            .padding(
                MaterialTheme.spacing.spaceSmall
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceExtraSmall))
    {

        Image(
            modifier = Modifier.width(MaterialTheme.spacing.spaceMedium),
            painter = painterResource(R.drawable.ic_add_primary),
            contentDescription = stringResource(id = R.string.text_create_topic, searchQuery),
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = "Create '${searchQuery}'",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}