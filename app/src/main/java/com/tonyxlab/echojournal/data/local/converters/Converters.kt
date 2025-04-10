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
class Converters
    @Inject
    constructor(private val jsonSerializer: JsonSerializer) {
        private val instantSerializer: KSerializer<Instant> = Instant.serializer()

        private val topicsSerializer: KSerializer<List<String>> = ListSerializer(String.serializer())

        private val moodSerializer: KSerializer<Mood> = Mood.serializer()

        @TypeConverter
        fun writeInstant(instant: Instant): String {
            return jsonSerializer.toJson(instantSerializer, instant)
        }

        @TypeConverter
        fun readInstant(json: String): Instant {
            return jsonSerializer.fromJson(instantSerializer, json)
        }

        @TypeConverter
        fun writeMood(mood: Mood): String {
            return jsonSerializer.toJson(moodSerializer, mood)
        }

        @TypeConverter
        fun readMood(json: String): Mood {
            return jsonSerializer.fromJson(moodSerializer, json)
        }

        @TypeConverter
        fun writeTopics(topics: List<String>): String {
            return jsonSerializer.toJson(topicsSerializer, topics)
        }

        @TypeConverter
        fun readTopics(json: String): List<String> {
            return jsonSerializer.fromJson(topicsSerializer, json)
        }
    }