package com.tonyxlab.echojournal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.Secondary70
import com.tonyxlab.echojournal.presentation.ui.theme.Secondary95
import com.tonyxlab.echojournal.presentation.ui.theme.spacing

@Composable
 fun BasicEntryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    iconContent: @Composable (() -> Unit)? = null,
    hintColor: Color = MaterialTheme.colorScheme.outlineVariant,
    isHeadline: Boolean = false,
    gap: Dp = 6.dp,
) {
    val focusManager = LocalFocusManager.current
    val bodyMediumTextStyle = TextStyle(
        color = MaterialTheme.colorScheme.outlineVariant,
        fontSize = 14.sp,
        fontWeight = FontWeight.W400
    )
    var isFocused by remember { mutableStateOf(false) }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(gap),
        verticalAlignment = Alignment.CenterVertically
    ) {

        iconContent?.invoke()

        BasicTextField(
            modifier = Modifier.onFocusChanged { isFocused = it.isFocused },
            value = value,
            onValueChange = onValueChange,
            textStyle = bodyMediumTextStyle,
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
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                .padding(MaterialTheme.spacing.spaceMedium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {

            BasicEntryTextField(
                value = "",
                onValueChange = {},
                isHeadline = true,
                hint = "Add Something ...",
                iconContent = {
                    AppIcon(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = Secondary95)
                            .size(MaterialTheme.spacing.spaceLarge),
                        imageVector = Icons.Default.Add,
                        tint = Secondary70
                    )
                }
            )

            BasicEntryTextField(
                value = "Work",
                onValueChange = {},
                isHeadline = true,
                hint = "Add Something ...",
                iconContent = {
                    AppIcon(
                        modifier = Modifier
                            .size(
                                width = MaterialTheme.spacing.spaceMedium,
                                height = MaterialTheme.spacing.spaceExtraSmall * 5
                            ),
                        imageVector = Icons.Default.Edit,
                        tint = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            )
        }
    }
}
