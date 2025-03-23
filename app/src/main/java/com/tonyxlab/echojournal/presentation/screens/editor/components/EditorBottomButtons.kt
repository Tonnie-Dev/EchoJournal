package com.tonyxlab.echojournal.presentation.screens.editor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tonyxlab.echojournal.presentation.core.utils.spacing

@Composable
fun EditorBottomButtons(
    primaryButtonText: String,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
    primaryButtonEnabled: Boolean = true,
    primaryLeadingIcon: (@Composable () -> Unit)? = null

) {
    Row(
        modifier = modifier.height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
    ) {


    }j
}