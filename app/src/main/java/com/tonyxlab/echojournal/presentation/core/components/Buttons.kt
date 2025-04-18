package com.tonyxlab.echojournal.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.echojournal.R
import com.tonyxlab.echojournal.presentation.core.utils.GradientScheme
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.theme.EchoJournalTheme

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
) {
    Button(
        modifier =
            modifier
                .clip(CircleShape)
                .background(
                    brush =
                        if (enabled) {
                            GradientScheme.PrimaryGradient
                        } else {
                            GradientScheme.DisabledSolidColor
                        },
                    shape = CircleShape,
                ),
        onClick = onClick,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        enabled = enabled,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceDoubleDp * 3),
        ) {
            leadingIcon?.let { leadingIcon() }
            Text(
                text = text,
                style =
                    MaterialTheme.typography.bodyLarge.copy(
                        color =
                            if (enabled) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.outline
                            },
                    ),
            )
        }
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
    ) {
        Text(
            text = text,
            style =
                MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                ),
        )
    }
}

@PreviewLightDark
@Composable
private fun ButtonsPreview() {
    EchoJournalTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(MaterialTheme.spacing.spaceMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium),
        ) {
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(.6f),
                text = stringResource(id = R.string.button_text_save),
                onClick = {},
                enabled = false,
            )
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(.6f),
                text = stringResource(id = R.string.button_text_save),
                onClick = {},
                enabled = true,
            )
            SecondaryButton(
                modifier = Modifier.fillMaxWidth(.6f),
                text = stringResource(id = R.string.button_text_cancel),
                onClick = {},
            )
        }
    }
}