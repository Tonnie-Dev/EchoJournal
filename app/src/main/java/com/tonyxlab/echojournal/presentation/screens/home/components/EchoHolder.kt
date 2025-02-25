package com.tonyxlab.echojournal.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
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
