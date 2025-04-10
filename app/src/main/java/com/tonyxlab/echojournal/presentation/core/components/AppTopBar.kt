package com.tonyxlab.echojournal.presentation.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.theme.EchoJournalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleSmall,
    isShowBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(end = MaterialTheme.spacing.spaceLarge),
                text = title,
                textAlign = if (isShowBackButton) TextAlign.Center else TextAlign.Start,
                style = style,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        navigationIcon = {
            if (isShowBackButton) {
                IconButton(onClick = { onBackClick?.invoke() }) {
                    Icon(
                        modifier = Modifier.minimumInteractiveComponentSize(),
                        imageVector = Icons.AutoMirrored.Filled.NavigateBefore,
                        contentDescription =
                            stringResource(
                                R.string.text_back,
                            ),
                    )
                }
            }
        },
    )
}

@PreviewLightDark
@Composable
private fun AppTopBarPreview() {
    EchoJournalTheme {
        Column {
            AppTopBar(title = "Journal", isShowBackButton = true)
            AppTopBar(title = "Journal", isShowBackButton = false)
        }
    }
}