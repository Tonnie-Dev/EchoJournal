package com.tonyxlab.echojournal.presentation.core.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AppIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector = Icons.Default.Add,
    tint: Color = MaterialTheme.colorScheme.onPrimary,
    contentDescription: String = "",
) {
    Icon(
        modifier = modifier,
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = tint,
    )
}