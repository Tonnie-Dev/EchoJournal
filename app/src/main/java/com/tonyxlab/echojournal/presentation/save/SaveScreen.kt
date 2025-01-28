package com.tonyxlab.echojournal.presentation.save

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.util.fastForEach
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.domain.model.Mood.*
import com.tonyxlab.echojournal.presentation.components.AppButton
import com.tonyxlab.echojournal.presentation.components.AppIcon
import com.tonyxlab.echojournal.presentation.components.AppTopBar
import com.tonyxlab.echojournal.presentation.components.BasicEntryTextField
import com.tonyxlab.echojournal.presentation.components.PlayTrackUnit
import com.tonyxlab.echojournal.presentation.components.TopicSelector
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
                .fillMaxSize()
                .padding(paddingValues),

            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {

            BasicEntryTextField(
                value = titleText,
                onValueChange = onDescValueChange,
                hint = stringResource(id = R.string.text_add_title),
                isHeadline = true,
                gap = MaterialTheme.spacing.spaceDoubleDp * 3,
                leadingContent = {
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

             TopicSelector()

            BasicEntryTextField(
                value = titleText,
                onValueChange = onDescValueChange,
                hint = stringResource(id = R.string.text_add_title),
                isHeadline = false,
                gap = MaterialTheme.spacing.spaceDoubleDp * 3,
                leadingContent = {
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

            MoodSelectorBottomSheet()

        }


    }


}



@Composable
fun MoodSelectorBottomSheet(modifier: Modifier = Modifier) {


    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = stringResource(R.string.text_how_are_you_doing),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceLarge))

        Row(horizontalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.spaceSmall)) {

            val moods = listOf(Stressed, Sad, Neutral, Peaceful, Excited)

            moods.fastForEach {

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceSmall),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(painter = painterResource(it.icon), contentDescription = it.name)
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }


            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceExtraSmall * 3))
        Row(modifier = Modifier.fillMaxWidth()) {


            AppButton(

                buttonText = stringResource(id = R.string.text_cancel)
            )
            AppButton(
                modifier = Modifier.weight(1f),
                buttonText = stringResource(id = R.string.text_save)
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