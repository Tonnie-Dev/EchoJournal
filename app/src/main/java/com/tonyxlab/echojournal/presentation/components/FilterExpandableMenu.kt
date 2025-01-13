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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.LocalSpacing
import com.tonyxlab.echojournal.presentation.ui.theme.buttonSmallTextStyle
import com.tonyxlab.echojournal.utils.addConditionalModifier

@Composable
fun FilterExpandableMenu(
    selectionMode: SelectionMode,
    moods: List<Mood>,
    topics: List<String>,
    onClickItem: () -> Unit, modifier: Modifier = Modifier
) {

    var isExpanded by remember { mutableStateOf(true) }

    val menuWidth = LocalConfiguration.current.screenWidthDp.dp - 422.dp
    MaterialTheme(
            shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(10.dp))
    ) {
        Box(
                modifier = modifier
                        .fillMaxWidth()
        ) {
            DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = {  },
                    offset = DpOffset(16.dp, 0.dp),
                    modifier = Modifier
                            .width(menuWidth)
                            .background(MaterialTheme.colorScheme.onPrimary)
                            .padding(0.dp),
            ) {


        SelectionMenu(
                selectionMode = selectionMode,
                moods = moods,
                topics = topics,
                onClickItem = onClickItem
        )}}
    }

}

@Composable
fun SelectionMenu(
    selectionMode: SelectionMode,
    moods: List<Mood>,
    topics: List<String>,
    onClickItem: () -> Unit,

    ) {
    when (selectionMode) {

        SelectionMode.MOOD -> {

            SelectionRow(
                    items = moods,

                    rowContent = {

                        MoodSelectionItem(mood = it, onSelectMoodItem = onClickItem)
                    }
            )
        }

        SelectionMode.TOPIC -> {


            SelectionRow(
                    items = topics,

                    rowContent = {

                        TopicSelectionItem(topic = it, onClickTopicItem = onClickItem)
                    }
            )
        }
    }
}

@Composable
private fun <T> SelectionRow(
    items: List<T>,
    rowContent: @Composable (T) -> Unit,
    modifier: Modifier = Modifier
) {


    Column(
            modifier = modifier

                    .wrapContentHeight()
    ) {

        repeat(items.size) {

            rowContent(items[it])
        }
    }
}

@Composable
private fun MoodSelectionItem(
    mood: Mood,
    onSelectMoodItem: () -> Unit,
) {
    val spacing = LocalSpacing.current
    var isSelected by remember { mutableStateOf(false) }
    Row(
            modifier = Modifier
                    .addConditionalModifier(isSelected) {
                        background(
                                color = MaterialTheme.colorScheme.surfaceTint,
                                shape = RoundedCornerShape(
                                        spacing.spaceSmall
                                )
                        )
                    }
                    .height(spacing.spaceTen * 4)
                    .clickable {
                        onSelectMoodItem()
                        isSelected = isSelected.not()
                    }
                    .padding(
                            horizontal = spacing.spaceExtraSmall,
                            vertical = spacing.spaceDoubleDp
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
) {
    val spacing = LocalSpacing.current
    var isSelected by remember { mutableStateOf(false) }
    Row(
            modifier = Modifier
                    .addConditionalModifier(isSelected) {
                        background(
                                color = MaterialTheme.colorScheme.surfaceTint,
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
                            horizontal = spacing.spaceExtraSmall,
                            vertical = spacing.spaceDoubleDp
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

@PreviewLightDark
@Composable
private fun FilterExpandedMenuPreview() {

    val moods = listOf(Mood.Sad, Mood.Neutral, Mood.Stressed, Mood.Peaceful, Mood.Excited)
    EchoJournalTheme {

        Surface {

            FilterExpandableMenu(
                    selectionMode = SelectionMode.MOOD,
                    moods = moods,
                    topics = emptyList(),
                    onClickItem = {},
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

    MOOD, TOPIC
}

