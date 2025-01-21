package com.tonyxlab.echojournal.presentation.save

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.components.AppTopBar
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme

@Composable
fun SaveScreenContent(
    text: String,
    onValueChange: (String) -> Unit,
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


        }
    }


}


@PreviewLightDark
@Composable
private fun SaveScreenPreview() {
    EchoJournalTheme {


        SaveScreenContent(text = "", onValueChange = {})
    }
}