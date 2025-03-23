package com.tonyxlab.echojournal.presentation.screens.editor.components

import android.R.attr.spacing
import android.R.attr.text
import android.view.textservice.SpellCheckerInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tonyxlab.echojournal.presentation.core.utils.spacing

@Composable
fun ExitDialog(
    headline: String
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    supportingText: String,
    confirmButtonText: String = "",
    cancelButtonText: String = ""
) {

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {


        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.spaceMedium),
            shape = RoundedCornerShape(
                MaterialTheme.spacing.spaceSmall
            )
        ) {

            Dialogh
        }
    }
}

@Composable
fun DialogHeader(
    headline: String,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
) {
    Column(modifier = modifier) {

        // Headline
        Text(
            text = headline,
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceExtraSmall))

        if (supportingText != null) {

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceMedium))

            // Supporting Text
            Text(
                text = supportingText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}