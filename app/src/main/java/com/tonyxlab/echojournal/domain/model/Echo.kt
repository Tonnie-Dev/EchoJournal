package com.tonyxlab.echojournal.domain.model

import java.sql.Timestamp


data class Echo(
    val id: String,
    val name: String,
    val description: String,
    val timeStamp: Long,
    val length: Int,
    val mood: Mood,
    val topics: List<String>
)