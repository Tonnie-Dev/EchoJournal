package com.tonyxlab.echojournal.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.util.fastForEach
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.LocalSpacing

@Composable
fun MoodsChipRow(
    moods: List<Mood>,
    isFocussed: Boolean,
    onClickChip: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current



    Row(
            modifier = modifier
                    .background(
                            color = Color.White,
                            RoundedCornerShape(spacing.spaceMedium)
                    )
                    .border(
                            width = spacing.spaceSingleDp,
                            color = if (isFocussed)
                                MaterialTheme.colorScheme.primaryContainer
                            else
                                MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(spacing.spaceMedium)
                    )
                    .padding(
                            top = spacing.spaceExtraSmall,
                            bottom = spacing.spaceExtraSmall,
                            start = spacing.spaceExtraSmall,
                            end = spacing.spaceSmall
                    )
                    .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.spaceSmall)
    ) {

        moods.ifEmpty {

            Text(text = stringResource(R.string.all_moods_filter_text))
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween) {

            moods.fastForEach {

                Image(painter = painterResource(it.icon), contentDescription = it.name)
            }
        }


        Text(
                modifier = Modifier
                        .wrapContentWidth()
                        .weight(.1f, false),
                text = moods.joinToString { it.name },
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
        )


        Icon(
                modifier = Modifier.size(spacing.spaceDoubleDp * 10),
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.close_icon_text),
                tint = MaterialTheme.colorScheme.secondaryContainer
        )

    }
}


@PreviewLightDark
@Composable
private fun MoodsChipPreview() {

    val spacing = LocalSpacing.current
    val moods = listOf(
            Mood.Excited,
            Mood.Peaceful,
            Mood.Neutral,
            Mood.Sad,
            Mood.Stressed
    )


    EchoJournalTheme {
        Column(
                modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(
                                spacing.spaceSmall
                        ),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {

            MoodsChipRow(moods = emptyList(), isFocussed = false, onClickChip = {})
            MoodsChipRow(moods = moods, isFocussed = true, onClickChip = {})
            MoodsChipRow(moods = moods, isFocussed = false, onClickChip = {})
            MoodsChipRow(moods = moods.take(1), isFocussed = false, onClickChip = {})
            MoodsChipRow(moods = moods.take(2), isFocussed = true, onClickChip = {})
            MoodsChipRow(moods = moods.take(3), isFocussed = false, onClickChip = {})
            MoodsChipRow(moods = moods.take(4), isFocussed = true, onClickChip = {})

        }

    }

}