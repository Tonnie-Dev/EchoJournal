package com.tonyxlab.echojournal.presentation.screens.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.util.fastFilter
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastJoinToString
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.toMood
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiState


@Composable
fun EchoFilter(
    filterState: HomeUiState.FilterState,
    onEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: Enclose Lazy Row in a Column if no Error is Detected
    /*
       Column(modifier = modifier){


       }
    */

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceDoubleDp * 3)
    ) {
        item {

            FilterChip(
                defaultTitle = stringResource(id = R.string.all_moods_filter_text),
                filterItems = filterState.moodFilterItems,
                isFilterItemSelected = filterState.isMoodFilterOpen,
                onClickFilter = { onEvent(HomeUiEvent.ActivateMoodFilter) },
                onClearFilter = { onEvent(HomeUiEvent.CancelMoodFilter) },
                leadingIcon = {
                    if (filterState.moodFilterItems.isNotEmpty()) {
                        SelectedMoodIcons(moodFilterItems = filterState.moodFilterItems)
                    }
                })
        }

        item {

            //Topic Chip
            FilterChip(
                defaultTitle = stringResource(id = R.string.all_topics_filter_text),
                filterItems = filterState.topicFilterItems,
                isFilterItemSelected = filterState.isTopicFilterOpen,
                onClickFilter = { onEvent(HomeUiEvent.ActivateTopicFilter) },
                onClearFilter = { onEvent(HomeUiEvent.CancelTopicFilter) }
            )
        }

    }

}

@Composable
fun SelectedMoodIcons(
    moodFilterItems: List<HomeUiState.FilterState.FilterItem>,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(-MaterialTheme.spacing.spaceExtraSmall)
    ) {
        moodFilterItems.fastForEach { filterItem ->
            val mood = filterItem.title.toMood()
            Image(
                modifier = Modifier.height(MaterialTheme.spacing.spaceDoubleDp * 11),
                painter = painterResource(mood.icon),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun FilterChip(
    defaultTitle: String,
    filterItems: List<HomeUiState.FilterState.FilterItem>,
    isFilterItemSelected: Boolean,
    onClickFilter: () -> Unit,
    onClearFilter: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null
) {

    AssistChip(
        modifier = modifier,
        enabled = enabled,
        onClick = onClickFilter,
        shape = RoundedCornerShape(MaterialTheme.spacing.spaceTen * 5),
        colors = AssistChipDefaults.assistChipColors(
            containerColor = when {
                isFilterItemSelected -> MaterialTheme.colorScheme.surface
                filterItems.isAnyMoodSelected() -> MaterialTheme.colorScheme.surface
                else -> MaterialTheme.colorScheme.background
            }
        ),
        border = BorderStroke(
            width = MaterialTheme.spacing.spaceSingleDp,
            color = if (isFilterItemSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.outlineVariant
        ),
        label = {

            Text(
                text = formatFilterText(defaultTitle, filterItems),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Medium
                )
            )
        },

        trailingIcon = {

            if (filterItems.isAnyMoodSelected()) {

                //Clear Icon
                IconButton(
                    modifier = Modifier.size(MaterialTheme.spacing.spaceMedium),
                    onClick = { onClearFilter() }

                ) {

                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(id = R.string.text_cancel),
                        tint = MaterialTheme.colorScheme.secondaryContainer
                    )
                }
            }
        },
        leadingIcon = leadingIcon
    )

}

private fun formatFilterText(
    defaultTitle: String,
    filterItems: List<HomeUiState.FilterState.FilterItem>
): String {

    val selectedItems = filterItems.fastFilter { it.isChecked }.map { it.title }

    return when {

        selectedItems.isEmpty() -> defaultTitle
        selectedItems.size == 1 -> selectedItems.first()
        selectedItems.size == 2 -> selectedItems.fastJoinToString(separator = ", ")
        else -> {

            val firstTwo = selectedItems.take(2).fastJoinToString(", ")

            "$firstTwo +${selectedItems.size.minus(2)}"
        }
    }
}

private fun List<HomeUiState.FilterState.FilterItem>.isAnyMoodSelected(): Boolean =
    this.any { it.isChecked }