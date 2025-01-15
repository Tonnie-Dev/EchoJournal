package com.tonyxlab.echojournal.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.LocalSpacing
import com.tonyxlab.echojournal.presentation.ui.theme.SurfaceTint05
import com.tonyxlab.echojournal.presentation.ui.theme.buttonSmallTextStyle
import com.tonyxlab.echojournal.utils.addConditionalModifier

@Composable
fun FilterExpandableMenu(
    moods: List<Mood>,
    topics: List<String>,
    modifier: Modifier = Modifier
) {

    val spacing = LocalSpacing.current

    var isExpanded by remember { mutableStateOf(false) }
    var mode by remember { mutableStateOf(SelectionMode.NONE) }

    Column(
            modifier = modifier
                    .padding(spacing.spaceMedium)
    ) {

        FilterRow(
                moods = moods,
                topics = topics,
                isMoodFilterClicked = mode == SelectionMode.MOOD,
                isTopicFilterClicked = mode == SelectionMode.TOPIC,
                onClickMoodFilter = {
                    mode = SelectionMode.MOOD
                    isExpanded = true
                },
                onClickTopicFilter = {
                    mode = SelectionMode.TOPIC
                    isExpanded = true
                },

                )
        ExpandableMenuContent(
                modifier = Modifier.fillMaxWidth(),
                isExpanded = isExpanded,
                selectionMode = mode,
                moods = moods,
                topics = topics,
                onClickItem = {},
                onDismiss = { isExpanded = false }
        )

    }

}



@Composable
fun ExpandableMenuContent(
    isExpanded: Boolean,
    selectionMode: SelectionMode,
    moods: List<Mood>,
    topics: List<String>,
    onDismiss: () -> Unit,
    onClickItem: () -> Unit,
    modifier: Modifier = Modifier
) {
val spacing = LocalSpacing.current
    var menuWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Box(
            modifier = modifier
                    .onGloballyPositioned {

                        menuWidth = with(density) {
                            it.size.width.toDp()
                        }

                    }
    ) {

        DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = onDismiss,
                modifier = Modifier
                        .background(color = Color.White)
                        .width(width = menuWidth)
                        .padding(
                                horizontal = spacing.spaceSmall,
                                vertical = spacing.spaceDoubleDp
                        )

        ) {

            SelectionMenu(
                    selectionMode = selectionMode,
                    moods = moods,
                    topics = topics,
                    onClickItem = onClickItem
            )
        }
    }

}


@Composable
fun SelectionMenu(
    selectionMode: SelectionMode,
    moods: List<Mood>,
    topics: List<String>,
    onClickItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (selectionMode) {

        SelectionMode.MOOD -> {

            SelectionPaneContent(
                    items = moods,

                    rowContent = {

                        MoodSelectionItem(
                                mood = it,
                                onSelectMoodItem = onClickItem,
                                modifier = modifier
                        )
                    }
            )
        }

        SelectionMode.TOPIC -> {

            SelectionPaneContent(
                    items = topics,

                    rowContent = {

                        TopicSelectionItem(
                                topic = it,
                                onClickTopicItem = onClickItem,
                                modifier = modifier
                        )
                    }
            )
        }

        else -> Unit
    }
}

@Composable
private fun <T> SelectionPaneContent(
    items: List<T>,
    rowContent: @Composable (T) -> Unit,
) {
    val spacing = LocalSpacing.current
Column (verticalArrangement = Arrangement.spacedBy(spacing.spaceDoubleDp)){


    repeat(items.size) {

        rowContent(items[it])
    }
}
}

@Composable
private fun MoodSelectionItem(
    mood: Mood,
    onSelectMoodItem: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    var isSelected by remember { mutableStateOf(false) }
    Row(
            modifier = modifier
                    .addConditionalModifier(isSelected) {
                        background(
                                color = SurfaceTint05,
                                shape = RoundedCornerShape(
                                        spacing.spaceSmall
                                )
                        )
                    }
                    .height(spacing.spaceTen * 4)
                    .fillMaxWidth()
                    .clickable {
                        onSelectMoodItem()
                        isSelected = isSelected.not()
                    }
                    .padding(
                            horizontal = spacing.spaceSmall,
                            vertical = spacing.spaceDoubleDp * 3
                    ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(spacing.spaceSmall),
                verticalAlignment = Alignment.CenterVertically
        ) {

            Image(painter = painterResource(mood.icon), contentDescription = mood.name)
            Text(
                    text = mood.name,
                    color = MaterialTheme.colorScheme.secondary,
                    style = buttonSmallTextStyle
            )

        }

        Icon(
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(R.string.icon_text_checkmark)
        )
    }


}


@Composable
private fun TopicSelectionItem(
    topic: String,
    onClickTopicItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    var isSelected by remember { mutableStateOf(false) }
    Row(
            modifier = modifier
                    .addConditionalModifier(isSelected) {
                        background(
                                color = SurfaceTint05,
                                shape = RoundedCornerShape(
                                        spacing.spaceSmall
                                )
                        )
                    }
                    .height(spacing.spaceTen * 4)
                    .clickable {
                        onClickTopicItem()
                        isSelected = isSelected.not()
                    }
                    .padding(
                            horizontal = spacing.spaceSmall,
                            vertical = spacing.spaceDoubleDp * 3
                    ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(spacing.spaceSmall),
                verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                    text = stringResource(R.string.hash_tag_text),
                    color = MaterialTheme.colorScheme.secondary,
                    style = buttonSmallTextStyle
            )
            Text(
                    text = topic,
                    color = MaterialTheme.colorScheme.secondary,
                    style = buttonSmallTextStyle
            )

        }

        Icon(
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(R.string.icon_text_checkmark)
        )
    }


}

@Preview(showBackground = true)
@Composable
fun PreviewCustomDropdownMenuExample() {

    val moods = listOf(Mood.Sad, Mood.Neutral, Mood.Stressed, Mood.Peaceful, Mood.Excited)
    val topics = listOf("Work", "Money", "Music", "Love", "Weather", "War")
    FilterExpandableMenu(moods = moods.take(2), topics = topics)
}

@PreviewLightDark
@Composable
private fun ExpandablePreview() {

    val moods = listOf(Mood.Sad, Mood.Neutral, Mood.Stressed, Mood.Peaceful, Mood.Excited)
    EchoJournalTheme {

        Column(Modifier.background(MaterialTheme.colorScheme.surfaceVariant)) {

            ExpandableMenuContent(
                    selectionMode = SelectionMode.MOOD,
                    moods = moods,
                    topics = emptyList(),
                    onClickItem = {},
                    isExpanded = true,
                    onDismiss = {}
            )

        }


    }
}

@PreviewLightDark
@Composable
private fun TopicSelectionItemPreview() {
    val spacing = LocalSpacing.current
    EchoJournalTheme {

        Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {
            TopicSelectionItem(topic = "Work", onClickTopicItem = {})
            TopicSelectionItem(topic = "Friends", onClickTopicItem = {})
            TopicSelectionItem(topic = "Family", onClickTopicItem = {})
            TopicSelectionItem(topic = "Love", onClickTopicItem = {})
            TopicSelectionItem(topic = "Surprise", onClickTopicItem = {})

        }
    }

}


@PreviewLightDark
@Composable
private fun MoodSelectionPreview() {
    val spacing = LocalSpacing.current
    EchoJournalTheme {

        Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {
            MoodSelectionItem(mood = Mood.Excited, onSelectMoodItem = {})
            MoodSelectionItem(mood = Mood.Peaceful, onSelectMoodItem = {})
            MoodSelectionItem(mood = Mood.Neutral, onSelectMoodItem = {})
            MoodSelectionItem(mood = Mood.Sad, onSelectMoodItem = {})
            MoodSelectionItem(mood = Mood.Stressed, onSelectMoodItem = {})

        }
    }

}


enum class SelectionMode {

    MOOD, TOPIC, NONE
}

