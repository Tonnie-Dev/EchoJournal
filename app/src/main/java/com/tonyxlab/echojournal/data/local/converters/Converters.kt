package com.tonyxlab.echojournal.data.local.converters


import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.tonyxlab.echojournal.domain.json.JsonSerializer
import com.tonyxlab.echojournal.domain.model.Mood
import kotlinx.datetime.Instant
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import javax.inject.Inject

@ProvidedTypeConverter
class Converters @Inject constructor(private val serializer: JsonSerializer) {

    private val moodSerializer: KSerializer<Mood> = Mood.serializer()
    private val topicSerializer: KSerializer<List<String>> = ListSerializer(String.serializer())
    private val instantSerializer: KSerializer<Instant> = Instant.serializer()

    @TypeConverter
    fun writeMood(mood: Mood): String {

        return serializer.toJson(moodSerializer, mood)
    }

    @TypeConverter
    fun readMood(json: String): Mood {

        return serializer.fromJson(moodSerializer, json)
    }

    @TypeConverter
    fun writeTopic(topics: List<String>): String {

        return serializer.toJson(topicSerializer, topics)
    }


    @TypeConverter
    fun readTopics(json: String): List<String> {

        return serializer.fromJson(topicSerializer, json)
    }

    @TypeConverter
    fun writeInstant(instant: Instant): String {

        return serializer.toJson(instantSerializer, instant)
    }

    @TypeConverter
    fun readInstant(json: String): Instant {

        return serializer.fromJson(instantSerializer, json)
    }
}