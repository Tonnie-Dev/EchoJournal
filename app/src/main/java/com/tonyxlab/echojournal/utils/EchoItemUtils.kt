package com.tonyxlab.echojournal.utils

import android.net.Uri
import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.domain.model.Mood
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.util.UUID
import kotlin.random.Random
import kotlin.random.nextInt

fun generateRandomEchoItem(): Echo {

    val randomInt = Random.nextInt(0..100)
    return Echo(
        id = UUID.randomUUID().toString(),
        name = "Day of The Jackal",
        description = generateLoremIpsum(wordCount = 89),
        timestamp = LocalDateTime.now().toInstant(timeZone = TimeZone.currentSystemDefault())
            .toEpochMilliseconds(),
        length = randomInt,
        mood = Mood.Sad,
        topics = listOf("Topic 1", "Topic 2", "Topic 3"),
        uri = Uri.EMPTY
    )


}

fun generateRandomEchoItems(count: Int = 13): List<Echo> {

    return buildList {

        repeat(count) {


            add(generateRandomEchoItem())
        }
    }
}