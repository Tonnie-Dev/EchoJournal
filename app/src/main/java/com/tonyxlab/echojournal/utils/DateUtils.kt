
@file:OptIn(FormatStringsInDatetimeFormats::class)

package com.tonyxlab.echojournal.utils

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

// LocalDateTime Now()
fun LocalDateTime.Companion.now(): LocalDateTime {
    val instant = Clock.System.now()
    return instant.toLocalDateTime(TimeZone.currentSystemDefault())
}

// LocalDateTime -> Milliseconds
fun LocalDateTime.fromLocalDateTimeToDefaultTimestamp(): Long {
    return this.toInstant(timeZone = TimeZone.currentSystemDefault())
        .toEpochMilliseconds()
}

// MilliSecs in Current Timezone -> UTC
fun Long.toUtcTimeStamp(): Long {
    return Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(timeZone = TimeZone.currentSystemDefault())
        .toInstant(TimeZone.UTC)
        .toEpochMilliseconds()
}

// MilliSecs in UTC -> Current Timezone
fun Long.fromUtcTimestampToDefaultTimeStamp(): Long {
    return Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.UTC)
        .toInstant(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()
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

fun Long.formatMillisToNumber(): String {
    val hours = (this / (1000 * 60 * 60)) % 24
    val minutes = (this / (1000 * 60)) % 60
    val seconds = (this / 1000) % 60
    return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
}

fun Long.formatMillisToTime(): String {
    val durationInSeconds = this / 1000

    val localDateTime =
        Instant
            .fromEpochMilliseconds(this)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    val pattern =
        if (durationInSeconds < 3600) {
            "ss:SS"
        } else {
            "mm:ss:SS"
        }

    val format =
        LocalDateTime.Format {
            byUnicodePattern(pattern = pattern)
        }
    return localDateTime.format(format)
}

fun Long.formatToRelativeDay(): String {
    val pattern = "EEEE, MMM d"

    val format = LocalDate.Format { byUnicodePattern(pattern = pattern) }

    val currentDate =
        Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date

    val targetDate =
        Instant.fromEpochMilliseconds(this)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date

    return when (targetDate) {
        currentDate -> "Today"
        currentDate.minus(1, DateTimeUnit.DAY) -> "Yesterday"
        else -> targetDate.format(format)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun Instant.formatInstantToRelativeDay(): String {
    val pattern = DateTimeFormatter.ofPattern("EEEE, MMM d")
    val currentDate = ZonedDateTime.now(ZoneId.systemDefault()).toLocalDate()
    val targetDate =
        this.toEpochMilliseconds().let {
            ZonedDateTime.ofInstant(java.time.Instant.ofEpochMilli(it), ZoneId.systemDefault()).toLocalDate()
        }

    return when (targetDate) {
        currentDate -> "Today"
        currentDate.minusDays(1) -> "Yesterday"
        else -> targetDate.format(pattern)
    }
}

fun Instant.formatHoursAndMinutes(): String  {
    val pattern = "HH:mm"
    val format = LocalDateTime.Format { byUnicodePattern(pattern = pattern) }

    val time = this.toLocalDateTime(TimeZone.currentSystemDefault())

    return time.format(format)
}

fun Long.toAmPmTime(): String {
    val pattern = "hh:mm aa"
    val date = Date(this)

    return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
}
