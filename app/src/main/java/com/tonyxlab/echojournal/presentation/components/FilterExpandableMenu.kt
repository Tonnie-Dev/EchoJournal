package com.tonyxlab.echojournal.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.LocalSpacing
import com.tonyxlab.echojournal.presentation.ui.theme.buttonSmallTextStyle
import com.tonyxlab.echojournal.utils.addConditionalModifier

@Composable
fun FilterExpandableMenu(modifier: Modifier = Modifier) {

}

@Composable
fun SelectionMenu(modifier: Modifier = Modifier) {

}

@Composable
fun SelectionRow(isSelected: Boolean, modifier: Modifier = Modifier) {


}

@Composable
private fun MoodSelectionItem(
    mood: Mood,
    isSelected: Boolean
) {
    val spacing = LocalSpacing.current
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
    isSelected: Boolean
) {
    val spacing = LocalSpacing.current
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
private fun TopicSelectionItemPreview() {
    val spacing = LocalSpacing.current
    EchoJournalTheme {

        Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {
            TopicSelectionItem(topic = "Work", isSelected = false)
            TopicSelectionItem(topic = "Friends", isSelected = true)
            TopicSelectionItem(topic = "Family", isSelected = true)
            TopicSelectionItem(topic = "Love",isSelected = false)
            TopicSelectionItem(topic = "Surprise", isSelected = false)

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
            MoodSelectionItem(mood = Mood.Excited, isSelected = false)
            MoodSelectionItem(mood = Mood.Peaceful, isSelected = true)
            MoodSelectionItem(mood = Mood.Neutral, isSelected = true)
            MoodSelectionItem(mood = Mood.Sad, isSelected = false)
            MoodSelectionItem(mood = Mood.Stressed, isSelected = false)

        }
    }

}

