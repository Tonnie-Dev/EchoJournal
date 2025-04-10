package com.tonyxlab.echojournal.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.conditionalModifier(
    condition: Boolean,
    modifier: @Composable Modifier.() -> Modifier,
): Modifier {
    return if (condition) {
        this.then(modifier())
    } else {
        this
    }
}
