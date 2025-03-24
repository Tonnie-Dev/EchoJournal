package com.tonyxlab.echojournal.presentation.screens.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.tonyxlab.echojournal.presentation.core.components.ExpandableText
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiState
import com.tonyxlab.echojournal.presentation.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.theme.EchoUltraLightGray
import com.tonyxlab.echojournal.utils.formatMillisToTime

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EchoHolder(
    echoHolderState: HomeUiState.EchoHolderState,
    echoPosition: EchoListPosition,
    onEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val echo = echoHolderState.echo
    val mood = echo.mood

    Row(modifier = modifier.height(IntrinsicSize.Min)) {

        var holderHeight by remember { mutableIntStateOf(0) }
        var isHolderCollapsed by remember { mutableStateOf(false) }

        MoodTimeline(
            moodRes = mood.icon,
            echoPosition = echoPosition,
            modifier = Modifier.fillMaxHeight(),
            isHolderCollapsed = isHolderCollapsed,
            holderHeight = holderHeight
        )

        Surface(modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged { size ->
                with(size.height) {
                    if (this != holderHeight) {
                        isHolderCollapsed = this < holderHeight
                        holderHeight = this
                    }
                }

            }
            .padding(vertical = MaterialTheme.spacing.spaceSmall),
            shape = RoundedCornerShape(MaterialTheme.spacing.spaceDoubleDp * 5),
            shadowElevation = MaterialTheme.spacing.spaceDoubleDp * 3) {

            Column(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.spaceDoubleDp * 7)
                    .padding(
                        top = MaterialTheme.spacing.spaceDoubleDp * 6,
                        bottom = MaterialTheme.spacing.spaceDoubleDp * 7
                    )
            ) {

                EchoHeader(
                    title = echo.title,
                    creationTime = "TODO"
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceDoubleDp * 5))

                MoodPlayer(mood = mood,
                    playerState = echoHolderState.playerState,
                    onPlayClick = { onEvent(HomeUiEvent.StartPlay(echoId = echo.id)) },
                    onPauseClick = { onEvent(HomeUiEvent.PausePlay(echoId = echo.id)) },
                    onResumeClick = { onEvent(HomeUiEvent.StartPlay(echoId = echo.id)) }
                )

                // Echo Description

                if (echo.description.isNotEmpty()) {

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceDoubleDp * 5))
                    ExpandableText(echoText = echo.description)
                }

                // Topic Tags
                if (echo.topics.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceSmall))
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceDoubleDp * 3),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceDoubleDp * 3)
                    ) {
                        echo.topics.forEach { topic -> TopicChip(title = topic) }
                    }
                }
            }
        }
    }

}

@Composable
private fun EchoHeader(
    title: String,
    creationTime: String,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier.fillMaxWidth(),
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

            if (echoPosition is EchoListPosition.Middle || echoPosition is EchoListPosition.Last)
                0
            else centerMoodOffsetY
        }
    }

    val dividerHeight by remember(holderHeight, echoPosition) {

        derivedStateOf {

            when (echoPosition) {
                EchoListPosition.First -> elementHeight - centerMoodOffsetY
                EchoListPosition.Last -> centerMoodOffsetY
                EchoListPosition.Middle -> if (isHolderCollapsed) holderHeight else elementHeight
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


