package com.tonyxlab.echojournal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
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
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import com.tonyxlab.echojournal.presentation.ui.theme.buttonMediumTextStyle
import com.tonyxlab.echojournal.presentation.ui.theme.gradient
import com.tonyxlab.echojournal.presentation.ui.theme.spacing
import com.tonyxlab.echojournal.utils.addConditionalModifier

@Composable
fun AppButton(
    buttonText: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isHighlighted: Boolean = false,
    leadingIcon: Boolean = false,
) {


    Box(
        modifier = modifier
            .clip(RoundedCornerShape(MaterialTheme.spacing.spaceMedium))

            .addConditionalModifier(isEnabled) {
                background(MaterialTheme.colorScheme.onPrimaryContainer)
            }
            .addConditionalModifier(isEnabled.not()) {
                background(MaterialTheme.colorScheme.surfaceVariant)
            }
            .addConditionalModifier(isHighlighted) {

                background(brush = MaterialTheme.gradient.buttonPressedGradient)
            },

        contentAlignment = Alignment.Center

    ) {

        Row(
            modifier = Modifier

                .padding(
                    horizontal = MaterialTheme.spacing.spaceMedium,
                    vertical = MaterialTheme.spacing.spaceSmall
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (leadingIcon) {

                AppIcon(
                    modifier = Modifier.size(MaterialTheme.spacing.spaceMedium),
                    imageVector = Icons.Default.Done,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.spaceExtraSmall))
            }

            Text(
                text = buttonText,
                style = buttonMediumTextStyle,

                color = when {
                    isHighlighted -> MaterialTheme.colorScheme.onPrimary
                    isEnabled -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.outline
                }


            )
        }
    }
}

@PreviewLightDark
@Composable
private fun AppButtonPreview() {
    EchoJournalTheme {


        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {

            AppButton(buttonText = stringResource(id = R.string.text_cancel))
            AppButton(buttonText = stringResource(id = R.string.text_save), isEnabled = false)
            AppButton(buttonText = stringResource(id = R.string.text_confirm), isHighlighted = true)
            AppButton(
                buttonText = stringResource(id = R.string.text_confirm),
                isEnabled = true,
                isHighlighted = true,
                leadingIcon = true
            )

        }
    }
}


