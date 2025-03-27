package com.tonyxlab.echojournal.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.domain.model.toMood
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiState.FilterState

@Composable
fun FilterList(
    filterItems: List<FilterState.FilterItem>,
    onItemClick: (String) -> Unit,
    onDismissClicked: () -> Unit,
    modifier: Modifier = Modifier,
    startOffset: IntOffset = IntOffset.Zero
) {
    Box(
        modifier = modifier
        .offset {
            startOffset
        }
        .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onDismissClicked() }
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(MaterialTheme.spacing.spaceTen),
            shadowElevation = MaterialTheme.spacing.spaceExtraSmall
        ) {
            LazyColumn(
                modifier = Modifier.padding(MaterialTheme.spacing.spaceExtraSmall),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceExtraSmall)
            ) {
                items(filterItems) { filterItem ->
                    FilterItem(
                        filterItem = filterItem,
                        onClick = { onItemClick(it) })
                }

            }

        }

    }
}


@Composable
fun FilterItem(
    filterItem: FilterState.FilterItem,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(MaterialTheme.spacing.spaceSmall))
            .fillMaxWidth()
            .clickable { onClick(filterItem.title) },
        shape = RoundedCornerShape(MaterialTheme.spacing.spaceSmall),
        color = if (filterItem.isChecked)
            MaterialTheme.colorScheme.surfaceTint.copy(alpha = .05f)
        else
            MaterialTheme.colorScheme.surface

    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = MaterialTheme.spacing.spaceTen,
                vertical = MaterialTheme.spacing.spaceDoubleDp * 3
            ), horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.spaceSmall
            ), verticalAlignment = Alignment.CenterVertically
        ) {

            val mood = filterItem.title.toMood()

            Image(
                modifier = Modifier.height(MaterialTheme.spacing.spaceSmall * 3),
                painter = if (mood == Mood.Undefined)
                    painterResource(R.drawable.ic_hashtag)
                else
                    painterResource(mood.icon),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.weight(1f),
                text = filterItem.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Medium
                )
            )

            if (filterItem.isChecked) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

}