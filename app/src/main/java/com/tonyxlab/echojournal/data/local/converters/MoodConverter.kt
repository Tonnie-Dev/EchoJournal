package com.tonyxlab.echojournal.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.tonyxlab.echojournal.domain.json.JsonSerializer
import com.tonyxlab.echojournal.domain.model.Mood
import kotlinx.serialization.KSerializer
import javax.inject.Inject


@ProvidedTypeConverter
class MoodConverter @Inject constructor(private val jsonSerializer: JsonSerializer) {

    private val moodSerializer: KSerializer<Mood> = Mood.serializer()
    @TypeConverter
    fun writeMood(mood: Mood): String {

        return jsonSerializer.toJson(moodSerializer, mood)
    }

    @TypeConverter
    fun readMood(json: String): Mood {

        return jsonSerializer.fromJson(moodSerializer, json)
    }

}