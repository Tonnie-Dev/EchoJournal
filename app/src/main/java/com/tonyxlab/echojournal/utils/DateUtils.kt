package com.tonyxlab.echojournal.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//Time Now
fun LocalDateTime.Companion.now(): LocalDateTime {
    val instant = Clock.System.now()
    return instant.toLocalDateTime(TimeZone.currentSystemDefault())
}


// In DB - EchoEntity
fun LocalDateTime.fromLocalDateTimeToUtcTimeStamp(): Long {

    return this.toInstant(timeZone = TimeZone.currentSystemDefault())
        .toLocalDateTime(timeZone = TimeZone.currentSystemDefault())
        .toInstant(TimeZone.UTC)
        .toEpochMilliseconds()

}

// From DB - Echo
fun Long.fromUtcTimeStampToLocalDateTime(): LocalDateTime {

    return Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.UTC)
        .toInstant(TimeZone.currentSystemDefault())
        .toLocalDateTime(TimeZone.currentSystemDefault())
}

fun Long.toAmPmTime(): String {

    val pattern = "hh:mm aa"
    val date = Date(this)

    return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
}
