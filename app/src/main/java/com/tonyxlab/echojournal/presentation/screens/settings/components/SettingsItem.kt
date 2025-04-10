package com.tonyxlab.echojournal.presentation.screens.settings.components

import android.R.attr.description
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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