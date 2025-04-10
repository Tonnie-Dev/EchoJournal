package com.tonyxlab.echojournal.utils

data class TextFieldValue<T>(
    val value: T,
    val isError: Boolean = false,
    val onValueChange: ((T) -> Unit)? = null,
)
