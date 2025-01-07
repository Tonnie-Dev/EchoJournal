package com.tonyxlab.echojournal.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.LocalSpacing

@Composable
fun EmptyScreen(modifier: Modifier = Modifier) {
    val spacing = LocalSpacing.current

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {


        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                    painter = painterResource(R.drawable.empty_screen_icon),
                    contentDescription = "Empty Icon"
            )
            //Text(text = )

        }
    }
}

@PreviewLightDark
@Composable
private fun EmptyScreenPreview() {
    EchoJournalTheme {

        Surface {

            EmptyScreen()
        }
    }
}
