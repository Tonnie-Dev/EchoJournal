package com.tonyxlab.echojournal.presentation.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.core.utils.spacing

@Composable
fun PermissionDialog(
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    navigateToAppSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            Column(modifier = Modifier.fillMaxWidth()) {
                HorizontalDivider()
                Text(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (isPermanentlyDeclined) {
                                    navigateToAppSettings()
                                } else {
                                    onOkClick()
                                }
                            }
                            .padding(MaterialTheme.spacing.spaceMedium),
                    text =
                        if (isPermanentlyDeclined) {
                            stringResource(id = R.string.grant_permission)
                        } else {
                            stringResource(id = R.string.ok)
                        },
                    style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
                )
            }
        },
        title = {
            Text(
                text =
                    if (isPermanentlyDeclined) {
                        stringResource(id = R.string.permission_dialog_declined_text)
                    } else {
                        stringResource(id = R.string.permission_dialog_text)
                    },
            )
        },
    )
}