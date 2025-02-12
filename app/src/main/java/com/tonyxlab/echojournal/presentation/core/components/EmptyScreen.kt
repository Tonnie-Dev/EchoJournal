package com.tonyxlab.echojournal.presentation.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.theme.LocalSpacing

@Composable
fun EmptyScreen(modifier: Modifier = Modifier) {
    val spacing = LocalSpacing.current

    Box(
            modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
    ) {


        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                    modifier = Modifier.padding(bottom = spacing.spaceLarge),
                    painter = painterResource(R.drawable.empty_screen_icon),
                    contentDescription = "Empty Icon"
            )
            Text(

                    modifier = Modifier.padding(bottom = spacing.spaceDoubleDp * 3),
                    text = stringResource(R.string.no_entries_text),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                    text = stringResource(R.string.start_recording_text),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun EmptyScreenPreview() {
    EchoJournalTheme {

        EmptyScreen()

       
    }
}
