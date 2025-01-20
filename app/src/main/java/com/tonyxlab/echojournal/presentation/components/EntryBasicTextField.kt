package com.tonyxlab.echojournal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.spacing

@Composable
private fun EntryBasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: ImageVector,
    hint: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    activeTextColor: Color = MaterialTheme.colorScheme.onSurface,
    hintColor: Color = MaterialTheme.colorScheme.outlineVariant,
    isHeadline: Boolean = false,
    gap: Dp = 6.dp,
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(gap),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = leadingIcon, contentDescription = stringResource(id = R.string.add_text))
        BasicTextField(
            modifier = Modifier.onFocusChanged { isFocused = it.isFocused },
            value = value,
            onValueChange = onValueChange,
            decorationBox = {
                Box(contentAlignment = Alignment.Center) {

                    if (value.isBlank() && isFocused.not()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = hint,
                            color = hintColor,
                            style = if (isHeadline)
                                MaterialTheme.typography.headlineLarge
                            else
                                MaterialTheme.typography.bodyMedium
                        )
                    } else if (value.isNotBlank()) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            it()

                        }
                    }
                }
            }
        )

    }

}


@PreviewLightDark
@Composable
private fun EntryBasicTextFieldPreview() {

    EchoJournalTheme {

        Column(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {


            EntryBasicTextField(
                value = "",
                onValueChange = {},
                leadingIcon = Icons.Default.Add,
                hint = "Add Something ..."
            )


            EntryBasicTextField(
                value = "Work",
                onValueChange = {},
                leadingIcon = Icons.Default.Add,
                hint = "Add Something ..."
            )
        }
    }
}
