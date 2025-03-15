package com.tonyxlab.echojournal.domain.model

import com.tonyxlab.echojournal.utils.Constants
import kotlinx.datetime.Clock.System
import kotlinx.datetime.Instant

data class Echo(
    val id: Long = Constants.INITIAL_DATABASE_ID,
    val title: String,
    val mood: Mood,
    val audionFilePath: String,
    val audioDuration: Int,
    val description: String = "",
    val topics: List<String> = emptyList(),
    val creationTimestamp: Instant = System.now()
)