package com.tonyxlab.echojournal.presentation.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.util.fastFilter
import androidx.compose.ui.util.fastJoinToString
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiState

@Composable
private fun FilterChip(
    defaultTitle: String,
    filterItems: List<HomeUiState.FilterState.FilterItem>,
    isFilterItemSelected: Boolean,
    onSelectItem: () -> Unit,
    onClearFilter: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null
) {

    AssistChip(
        modifier = modifier,
        enabled = enabled,
        onClick = onSelectItem,
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