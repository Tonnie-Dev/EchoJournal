package com.tonyxlab.echojournal.presentation.save

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.components.AppIcon
import com.tonyxlab.echojournal.presentation.components.AppTopBar
import com.tonyxlab.echojournal.presentation.components.BasicEntryTextField
import com.tonyxlab.echojournal.presentation.components.PlayTrackUnit
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.Secondary70
import com.tonyxlab.echojournal.presentation.ui.theme.Secondary90
import com.tonyxlab.echojournal.presentation.ui.theme.spacing

@Composable
fun SaveScreenContent(
    titleText: String,
    onTitleValueChange: (String) -> Unit,
    descText: String,
    onDescValueChange: (String) -> Unit,
    isPlaying: Boolean,
    seekValue: Float,
    onSeek: (Float) -> Unit,
    echoLength: Int,
    onTogglePlay: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(topBar = {

        AppTopBar(
            title = stringResource(R.string.title_new_entry),
            isShowBackButton = true
        )

    }) { paddingValues ->


        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            BasicEntryTextField(
                value = titleText,
                onValueChange = onDescValueChange,
                hint = stringResource(id = R.string.text_add_title),
                isHeadline = true,
                gap = MaterialTheme.spacing.spaceDoubleDp * 3,
                iconContent = {
                    AppIcon(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Secondary90)
                            .size(MaterialTheme.spacing.spaceLarge)
                            .padding(MaterialTheme.spacing.spaceDoubleDp * 3),
                        imageVector = Icons.Default.Add,
                        tint = Secondary70
                    )
                }
            )

            PlayTrackUnit(
                isPlaying = isPlaying,
                seekValue = seekValue,
                onSeek = onSeek,
                echoLength = echoLength,
                onTogglePlay = onTogglePlay
            )

        }


    }


}


@PreviewLightDark
@Composable
private fun SaveScreenPreview() {
    EchoJournalTheme {


        SaveScreenContent(
            titleText = "",
            onTitleValueChange = {},
            descText = "",
            onDescValueChange = {},
            isPlaying = false,
            seekValue = 0.0f,
            onSeek = {},
            echoLength = 0,
            onTogglePlay = {},

            )
    }
}