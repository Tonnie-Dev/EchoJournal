package com.tonyxlab.echojournal.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.core.utils.spacing

@Composable
fun EmptyHomeScreen(
    modifier: Modifier = Modifier,
    text: String = stringResource(id = R.string.text_no_entries_found),
    supportingText: String = stringResource(id = R.string.start_recording_text)
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(R.drawable.ic_empty_screen),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceLarge))

        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceDoubleDp * 3))

        Text(
            modifier = Modifier.widthIn(max = MaterialTheme.spacing.spaceTwoHundredFifty),
            text = supportingText,
            style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center)
        )
    }
}

@PreviewLightDark
@Composable
private fun EmptyScreenPreview() {
    EchoJournalTheme {
        EmptyHomeScreen()
    }
}
