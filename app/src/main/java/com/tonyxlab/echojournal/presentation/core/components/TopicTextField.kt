package com.tonyxlab.echojournal.presentation.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.screens.editor.components.EditorTextField
import com.tonyxlab.echojournal.presentation.theme.InterFontFamily

@Composable
fun RowScope.TopicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hintText: String = stringResource(id = R.string.text_topic),
    showLeadingIcon: Boolean = true
) {
    EditorTextField(
        modifier = modifier.align(alignment = Alignment.CenterVertically),
        textValue = value,
        onValueChange = onValueChange,
        hintText = hintText,
        leadingIcon = {
            if (showLeadingIcon) {
                Box(
                    modifier = Modifier
                        .width(MaterialTheme.spacing.spaceMedium)
                        .align(alignment = Alignment.CenterVertically),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "#",
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }
        }
    )
}