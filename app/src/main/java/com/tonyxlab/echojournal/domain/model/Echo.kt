package com.tonyxlab.echojournal.domain.model

import android.net.Uri

data class Echo(
    val id: String,
    val title: String,
    val description: String,
    val timestamp: Long,
    val duration: Int,
    val mood: Mood,
    val topics: List<String>,
    val uri:Uri
)