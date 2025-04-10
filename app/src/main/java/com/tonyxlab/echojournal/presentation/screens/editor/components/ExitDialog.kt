package com.tonyxlab.echojournal.presentation.screens.editor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tonyxlab.echojournal.presentation.core.utils.spacing

@Composable
fun ExitDialog(
    headline: String,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    supportingText: String = "",
    confirmButtonText: String = "",
    cancelButtonText: String = "",
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.spaceMedium),
            shape = RoundedCornerShape(MaterialTheme.spacing.spaceSmall),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.spaceMedium),
            ) {
                DialogHeader(
                    headline = headline,
                    supportingText = supportingText,
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceMedium))

                DialogButtons(
                    cancelButtonText = cancelButtonText,
                    confirmButtonText = confirmButtonText,
                    onConfirm = onConfirm,
                    onCancel = onDismissRequest,
                )
            }
        }
    }
}

@Composable
private fun DialogHeader(
    headline: String,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
) {
    Column(modifier = modifier) {
        // Headline
        Text(
            text = headline,
            style = MaterialTheme.typography.titleSmall,
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceExtraSmall))

        if (supportingText != null) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceMedium))

            // Supporting Text
            Text(
                text = supportingText,
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Normal,
                    ),
            )
        }
    }
}

@Composable
private fun DialogButtons(
    cancelButtonText: String,
    confirmButtonText: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Right,
    ) {
        // Cancel Button
        TextButton(onClick = onCancel) {
            Text(
                text = cancelButtonText,
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.secondary,
                    ),
            )
        }

        Spacer(modifier = Modifier.width(MaterialTheme.spacing.spaceMedium))

        // Confirm Button
        TextButton(
            onClick = onConfirm,
        ) {
            Text(
                text = confirmButtonText,
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.secondary,
                    ),
            )
        }
    }
}