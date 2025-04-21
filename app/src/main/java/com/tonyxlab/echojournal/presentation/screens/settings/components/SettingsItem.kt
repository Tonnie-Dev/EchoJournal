package com.tonyxlab.echojournal.presentation.screens.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.tonyxlab.echojournal.presentation.core.utils.spacing

@Composable
fun SettingsItem(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(MaterialTheme.spacing.spaceTen),
        shadowElevation = MaterialTheme.spacing.spaceExtraSmall,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.spaceMedium),
            verticalArrangement =
                Arrangement.spacedBy(
                    MaterialTheme.spacing.spaceTwelve,
                ),
        ) {
            SettingsHeader(
                title = title,
                description = description,
            )

            content()
        }
    }
}


@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String = "",
    icon: ImageVector,
    onClickSetting: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClickSetting)
            .padding(MaterialTheme.spacing.spaceSmall)
            .fillMaxWidth()
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(MaterialTheme.spacing.spaceMedium)
        )

        Column(modifier = Modifier.padding(MaterialTheme.spacing.spaceMedium)) {

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = subTitle,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}





