package com.tonyxlab.echojournal.presentation.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastJoinToString
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.presentation.core.utils.LocalSpacing
import com.tonyxlab.echojournal.presentation.theme.EchoJournalTheme
import com.tonyxlab.echojournal.utils.conditionalModifier

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterRow(
    moods: List<Mood>,
    topics: List<String>,
    isMoodFilterClicked: Boolean,
    isTopicFilterClicked: Boolean,
    onClickMoodFilter: () -> Unit,
    onClickTopicFilter: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    FlowRow(
        modifier =
            modifier.padding(
                top = spacing.spaceSmall,
                bottom = spacing.spaceSmall,
                start = spacing.spaceMedium,
                end = spacing.spaceMedium,
            ),
        horizontalArrangement = Arrangement.spacedBy(spacing.spaceSmall),
        maxLines = 2,
    ) {
        MoodFilter(
            moods = moods,
            isMoodFilterClicked = isMoodFilterClicked,
            onClickMoodFilter = onClickMoodFilter,
            modifier = Modifier.height(spacing.spaceLarge),
        )

        TopicFilter(
            topics = topics,
            isTopicFilterClicked = isTopicFilterClicked,
            onClickTopicFilter = onClickTopicFilter,
            modifier = Modifier.height(spacing.spaceLarge),
        )
    }
}

@Composable
fun MoodFilter(
    moods: List<Mood>,
    isMoodFilterClicked: Boolean,
    onClickMoodFilter: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current

    Row(
        modifier =
            modifier
                .conditionalModifier(isMoodFilterClicked) {
                    shadow(
                        elevation = spacing.spaceExtraSmall,
                        shape = RoundedCornerShape(spacing.spaceMedium),
                    )
                }
                .border(
                    width = spacing.spaceSingleDp,
                    color =
                        if (isMoodFilterClicked) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.outlineVariant
                        },
                    shape = RoundedCornerShape(spacing.spaceMedium),
                )
                .background(
                    color = Color.White,
                    RoundedCornerShape(spacing.spaceMedium),
                )
                .clickable {
                    onClickMoodFilter()
                }
                .padding(
                    top = spacing.spaceDoubleDp * 3,
                    bottom = spacing.spaceDoubleDp * 3,
                    start = spacing.spaceDoubleDp * 6,
                    end = spacing.spaceDoubleDp * 6,
                )
                .wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.spaceSmall),
    ) {
        if (moods.isEmpty()) {
            Text(
                modifier = Modifier.padding(horizontal = spacing.spaceSmall),
                text = stringResource(R.string.filter_text_all_moods),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelLarge,
            )
        } else {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                moods.fastForEach {
                    Image(painter = painterResource(it.icon), contentDescription = it.name)
                }
            }

            Text(
                modifier =
                    Modifier
                        .wrapContentWidth()
                        .weight(.1f, false),
                text = moods.fastJoinToString { it.name },
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Icon(
                modifier = Modifier.size(spacing.spaceDoubleDp * 10),
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.icon_close_text),
                tint = MaterialTheme.colorScheme.secondaryContainer,
            )
        }
    }
}

@Composable
fun TopicFilter(
    topics: List<String>,
    isTopicFilterClicked: Boolean,
    onClickTopicFilter: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    val topicCounter = if (topics.size > 2) topics.size.minus(2) else 0

    Row(
        modifier =
            modifier
                .conditionalModifier(isTopicFilterClicked) {
                    shadow(
                        elevation = spacing.spaceExtraSmall,
                        shape = RoundedCornerShape(spacing.spaceMedium),
                    )
                }
                .border(
                    width = spacing.spaceSingleDp,
                    color =
                        if (isTopicFilterClicked) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.outlineVariant
                        },
                    shape = RoundedCornerShape(spacing.spaceMedium),
                )
                .background(
                    color = Color.White,
                    RoundedCornerShape(spacing.spaceMedium),
                )
                .clickable {
                    onClickTopicFilter()
                }
                .padding(
                    top = spacing.spaceDoubleDp * 3,
                    bottom = spacing.spaceDoubleDp * 3,
                    start = spacing.spaceDoubleDp * 6,
                    end = spacing.spaceDoubleDp * 6,
                )
                .wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.spaceSmall),
    ) {
        if (topics.isEmpty()) {
            Text(
                modifier = Modifier.padding(horizontal = spacing.spaceSmall),
                text = stringResource(R.string.all_topics_filter_text),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelLarge,
            )
        } else {
            Text(
                modifier =
                    Modifier
                        .wrapContentWidth()
                        .weight(.1f, false),
                text =
                    topics.take(2)
                        .fastJoinToString { it }
                        .run {
                            if (topicCounter > 0) {
                                plus(" +$topicCounter")
                            } else {
                                this
                            }
                        },
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Icon(
                modifier = Modifier.size(spacing.spaceDoubleDp * 10),
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.icon_close_text),
                tint = MaterialTheme.colorScheme.secondaryContainer,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun FilterRowPreview() {
    val spacing = LocalSpacing.current
    val moods =
        listOf(
            Mood.Excited,
            Mood.Peaceful,
            Mood.Neutral,
            Mood.Sad,
            Mood.Stressed,
        )

    val topics =
        listOf(
            "Work",
            "Money",
            "Family",
            "Love",
            "Food",
            "School",
        )

    EchoJournalTheme {
        Column(
            modifier =
                Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .fillMaxWidth()
                    .padding(
                        horizontal = spacing.spaceMedium,
                        spacing.spaceExtraLarge,
                    ),
            verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium),
        ) {
            FilterRow(
                moods = moods.take(0),
                topics = topics.take(0),
                isMoodFilterClicked = false,
                isTopicFilterClicked = false,
                onClickMoodFilter = {},
                onClickTopicFilter = {},
            )

            FilterRow(
                moods = moods.take(1),
                topics = topics.take(0),
                isMoodFilterClicked = true,
                isTopicFilterClicked = false,
                onClickMoodFilter = {},
                onClickTopicFilter = {},
            )

            FilterRow(
                moods = moods.take(2),
                topics = topics.take(0),
                isMoodFilterClicked = true,
                isTopicFilterClicked = false,
                onClickMoodFilter = {},
                onClickTopicFilter = {},
            )

            FilterRow(
                moods = moods.take(3),
                topics = topics.take(0),
                isMoodFilterClicked = true,
                isTopicFilterClicked = false,
                onClickMoodFilter = {},
                onClickTopicFilter = {},
            )

            FilterRow(
                moods = moods.take(4),
                topics = topics.take(0),
                isMoodFilterClicked = true,
                isTopicFilterClicked = false,
                onClickMoodFilter = {},
                onClickTopicFilter = {},
            )

            FilterRow(
                moods = moods.take(5),
                topics = topics.take(0),
                isMoodFilterClicked = true,
                isTopicFilterClicked = false,
                onClickMoodFilter = {},
                onClickTopicFilter = {},
            )

            FilterRow(
                moods = moods.take(0),
                topics = topics.take(1),
                isMoodFilterClicked = false,
                isTopicFilterClicked = true,
                onClickMoodFilter = {},
                onClickTopicFilter = {},
            )

            FilterRow(
                moods = moods.take(0),
                topics = topics.take(2),
                isMoodFilterClicked = false,
                isTopicFilterClicked = true,
                onClickMoodFilter = {},
                onClickTopicFilter = {},
            )

            FilterRow(
                moods = moods.take(0),
                topics = topics.take(3),
                isMoodFilterClicked = false,
                isTopicFilterClicked = true,
                onClickMoodFilter = {},
                onClickTopicFilter = {},
            )

            FilterRow(
                moods = moods.take(0),
                topics = topics.take(4),
                isMoodFilterClicked = false,
                isTopicFilterClicked = true,
                onClickMoodFilter = {},
                onClickTopicFilter = {},
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun MoodFilterPreview() {
    val spacing = LocalSpacing.current
    val moods =
        listOf(
            Mood.Excited,
            Mood.Peaceful,
            Mood.Neutral,
            Mood.Sad,
            Mood.Stressed,
        )

    EchoJournalTheme {
        Column(
            modifier =
                Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(
                        spacing.spaceSmall,
                    ),
            verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium),
        ) {
            MoodFilter(moods = emptyList(), isMoodFilterClicked = false, onClickMoodFilter = {})
            MoodFilter(moods = moods, isMoodFilterClicked = true, onClickMoodFilter = {})
            MoodFilter(moods = moods, isMoodFilterClicked = false, onClickMoodFilter = {})
            MoodFilter(moods = moods.take(1), isMoodFilterClicked = false, onClickMoodFilter = {})
            MoodFilter(moods = moods.take(2), isMoodFilterClicked = true, onClickMoodFilter = {})
            MoodFilter(moods = moods.take(3), isMoodFilterClicked = false, onClickMoodFilter = {})
            MoodFilter(moods = moods.take(4), isMoodFilterClicked = true, onClickMoodFilter = {})
        }
    }
}

@PreviewLightDark
@Composable
private fun TopicFilterPreview() {
    val spacing = LocalSpacing.current
    val topics =
        listOf(
            "Work",
            "Money",
            "Family",
            "Love",
            "Food",
            "School",
        )

    EchoJournalTheme {
        Column(
            modifier =
                Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(
                        spacing.spaceSmall,
                    ),
            verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium),
        ) {
            TopicFilter(topics = emptyList(), isTopicFilterClicked = false, onClickTopicFilter = {})
            TopicFilter(topics = topics, isTopicFilterClicked = true, onClickTopicFilter = {})
            TopicFilter(topics = topics, isTopicFilterClicked = false, onClickTopicFilter = {})
            TopicFilter(
                topics = topics.take(1),
                isTopicFilterClicked = false,
                onClickTopicFilter = {},
            )
            TopicFilter(
                topics = topics.take(2),
                isTopicFilterClicked = true,
                onClickTopicFilter = {},
            )
            TopicFilter(
                topics = topics.take(3),
                isTopicFilterClicked = false,
                onClickTopicFilter = {},
            )
            TopicFilter(
                topics = topics.take(4),
                isTopicFilterClicked = true,
                onClickTopicFilter = {},
            )
        }
    }
}
