package com.tonyxlab.echojournal.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.presentation.components.EmptyScreen
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.LocalSpacing

@Composable
fun HomeScreen(
    list: List<Echo>, modifier: Modifier = Modifier
) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    list: List<Echo>,
    onAddEcho: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    Scaffold(modifier = modifier,
            topBar = {

                TopAppBar(title = {
                    Text(
                            text = "Your Echo Journal",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                    )
                })
            },
            floatingActionButton = {

                FloatingActionButton(
                        modifier = Modifier.size(spacing.spaceSmall * 8),
                        onClick = onAddEcho,
                        shape = CircleShape
                ) {

                    Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_text),
                            tint = MaterialTheme.colorScheme.onPrimary
                    )

                }
            }) { paddingValues ->


        list.ifEmpty {


            EmptyScreen(modifier = Modifier.padding(paddingValues = paddingValues))
        }
    }


}


@PreviewLightDark
@Composable
private fun HomeScreenContentPreview() {


    EchoJournalTheme {

        HomeScreenContent(list = emptyList(), onAddEcho = {})
    }
}