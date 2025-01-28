package com.tonyxlab.echojournal.domain.model

import android.net.Uri

data class Echo(
    val id: String,
    val name: String,
    val description: String,
    val timestamp: Long,
    val length: Int,
    val mood: Mood,
    val topics: List<String>,
    val uri:Uri
)