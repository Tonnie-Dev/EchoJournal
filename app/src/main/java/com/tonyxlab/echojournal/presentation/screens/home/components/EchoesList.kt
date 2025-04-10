
@file:RequiresApi(Build.VERSION_CODES.O)

package com.tonyxlab.echojournal.presentation.screens.home.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiEvent
import com.tonyxlab.echojournal.presentation.screens.home.handling.HomeUiState
import com.tonyxlab.echojournal.utils.formatInstantToRelativeDay
import kotlinx.datetime.Instant

@Composable
fun EchoesList(
    echoes: Map<Instant, List<HomeUiState.EchoHolderState>>,
    onEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = MaterialTheme.spacing.spaceDoubleDp * 10),
    ) {
        echoes.forEach { (timestamp, items) ->

            item {
                Text(
                    modifier =
                        Modifier.padding(
                            top = MaterialTheme.spacing.spaceSingleDp,
                            bottom = MaterialTheme.spacing.spaceSmall,
                        ),
                    text = timestamp.formatInstantToRelativeDay(),
                    style = MaterialTheme.typography.labelLarge,
                )
            }

            items(items = items, key = { holderState -> holderState.echo.id }) { echoHolderState ->
                EchoHolder(
                    echoHolderState = echoHolderState,
                    echoPosition =
                        when {
                            items.size == 1 -> EchoListPosition.Single
                            echoHolderState == items.first() -> EchoListPosition.First
                            echoHolderState == items.last() -> EchoListPosition.Last
                            else -> EchoListPosition.Middle
                        },
                    onEvent = onEvent,
                )
            }
        }
    }
}