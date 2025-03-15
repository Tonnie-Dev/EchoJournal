package com.tonyxlab.echojournal.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.tonyxlab.echojournal.domain.json.JsonSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import javax.inject.Inject


@ProvidedTypeConverter
class TopicsConverter @Inject constructor(private val jsonSerializer: JsonSerializer) {

    private val topicsSerializer: KSerializer<List<String>> = ListSerializer(String.serializer())

    @TypeConverter
    fun writeTopics(topics: List<String>): String {

        return jsonSerializer.toJson(topicsSerializer, topics)
    }

    @TypeConverter
    fun readTopics(json: String): List<String> {

        return jsonSerializer.fromJson(topicsSerializer, json)
    }

}