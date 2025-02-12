package com.tonyxlab.echojournal.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tonyxlab.echojournal.presentation.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.theme.Secondary70
import com.tonyxlab.echojournal.presentation.theme.Secondary95
import com.tonyxlab.echojournal.presentation.theme.spacing
import com.tonyxlab.echojournal.utils.TextFieldValue

@Composable
fun BasicEntryTextField(
    textFieldValue: TextFieldValue<String>,
    hint: String,
    modifier: Modifier = Modifier,
    leadingContent: @Composable (() -> Unit)? = null,
    hintColor: Color = MaterialTheme.colorScheme.outlineVariant,
    singleLine: Boolean = true,
    isHeadline: Boolean = false,
    gap: Dp = 6.dp,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default

) {

    val value = textFieldValue.value
    val headlineTextStyle = TextStyle(
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 26.sp,
        fontWeight = FontWeight.W500
    )
    val bodyMediumTextStyle = TextStyle(
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 14.sp,
        fontWeight = FontWeight.W400
    )

    var isFocused by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(gap),
        verticalAlignment = Alignment.Top
    ) {

        leadingContent?.invoke()

        BasicTextField(
            modifier = Modifier.onFocusChanged { isFocused = it.isFocused },
            value = value,
            onValueChange = { textFieldValue.onValueChange?.invoke(it) },
            textStyle = if (isHeadline)headlineTextStyle else bodyMediumTextStyle,
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.Center) {

                    if (value.isBlank() && isFocused.not()) {
                        Text(
                            text = hint,
                            color = hintColor,
                            style = if (isHeadline)
                                MaterialTheme.typography.headlineLarge
                            else
                                MaterialTheme.typography.bodyMedium
                        )
                    }

                    innerTextField()
                }
            },
            cursorBrush = SolidColor(Secondary70),
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )

    }

}


@PreviewLightDark
@Composable
private fun EntryBasicTextFieldPreview() {

    EchoJournalTheme {

        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize()
                .padding(MaterialTheme.spacing.spaceMedium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {

            BasicEntryTextField(
                textFieldValue = TextFieldValue(value = "My Heading"),
                isHeadline = true,
                hint = "Add Something ...",
                leadingContent = {
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
                textFieldValue = TextFieldValue("My Topic"),
                isHeadline = false,
                hint = "Add Something ...",
                leadingContent = {
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

            BasicEntryTextField(
                textFieldValue = TextFieldValue("My Description"),
                isHeadline = false,
                hint = "Add Something ...",
                leadingContent = {
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
