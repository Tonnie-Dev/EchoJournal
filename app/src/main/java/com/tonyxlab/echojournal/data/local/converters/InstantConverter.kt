package com.tonyxlab.echojournal.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.tonyxlab.echojournal.domain.json.JsonSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.KSerializer
import javax.inject.Inject


@ProvidedTypeConverter
class InstantConverter @Inject constructor(private val jsonSerializer: JsonSerializer) {

    private val instantSerializer: KSerializer<Instant> = Instant.serializer()

    @TypeConverter
    fun writeInstant(instant: Instant): String {

        return jsonSerializer.toJson(instantSerializer, instant)
    }

    @TypeConverter
    fun readInstant(json: String): Instant {

        return jsonSerializer.fromJson(instantSerializer, json)
    }
}