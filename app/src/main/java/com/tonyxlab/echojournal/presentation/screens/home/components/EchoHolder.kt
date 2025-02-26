package com.tonyxlab.echojournal.presentation.screens.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.theme.EchoUltraLightGray

@Composable
private fun EntryHeader(
    title: String,
    creationTime: String,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = creationTime,
            style = MaterialTheme.typography.labelMedium
        )
    }
}


@Composable
private fun TopicChip(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(MaterialTheme.spacing.spaceDoubleDp * 11)
            .clip(CircleShape)
            .background(
                color = EchoUltraLightGray,
                shape = CircleShape
            ), contentAlignment = Alignment.CenterStart
    ) {

        Text(
            modifier = Modifier.padding(MaterialTheme.spacing.spaceSmall),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .5f)
                    )
                ) {
                    append("#")
                }
                append(title)
            },
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@Composable
private fun MoodTimeline(
    @DrawableRes moodRes: Int,
    echoPosition: EchoListPosition,
    isHolderCollapsed: Boolean,
    holderHeight: Int,
    modifier: Modifier = Modifier,
    iconTopPadding: Dp = MaterialTheme.spacing.spaceMedium,
    iconEndPadding: Dp = MaterialTheme.spacing.spaceDoubleDp * 6
) {
    var elementHeight by remember { mutableIntStateOf(0) }
    var moodSize by remember { mutableStateOf(IntSize.Zero) }
    val centerMoodOffsetY by remember {

        derivedStateOf { moodSize.height / 2 + iconTopPadding.value.toInt() }
    }

    val dividerOffsetX by remember { derivedStateOf { moodSize.width / 2 } }
    val dividerOffsetY by remember(echoPosition) {

        derivedStateOf {

            if (echoPosition is EchoListPosition.Center || echoPosition is EchoListPosition.Bottom)
                0
            else centerMoodOffsetY
        }
    }

    val dividerHeight by remember(holderHeight, echoPosition) {

        derivedStateOf {

            when (echoPosition) {
                EchoListPosition.Top -> elementHeight - centerMoodOffsetY
                EchoListPosition.Bottom -> centerMoodOffsetY
                EchoListPosition.Center -> if (isHolderCollapsed) holderHeight else elementHeight
                EchoListPosition.Single -> 0
            }
        }
    }
    Box(modifier = modifier.onSizeChanged {

        elementHeight = it.height
    }) {

        VerticalDivider(
            modifier = Modifier
                .offset {
                    IntOffset(x = dividerOffsetX, y = dividerOffsetY)
                }
                .height(dividerHeight.dp),
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = .7f)
        )

        Image(
            modifier = Modifier
                .padding(top = iconTopPadding, end = iconEndPadding)
                .width(MaterialTheme.spacing.spaceLarge)
                .onSizeChanged { moodSize = it },
            painter = painterResource(moodRes),
            contentScale = ContentScale.FillWidth,
            contentDescription = null
        )
    }
}

@Composable
private fun TopicChipPreview() {


    EchoJournalTheme {


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TopicChip(
                title = "Tonnie",

                )
        }
    }
}


