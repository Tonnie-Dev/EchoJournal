package com.tonyxlab.echojournal.data.jsonimpl

import com.tonyxlab.echojournal.domain.json.JsonSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class JsonSerializerImpl : JsonSerializer {

    override fun <T> toJson(serializer: KSerializer<T>, data: T): String {
        return Json.encodeToString(serializer, data)
    }

    override fun <T> fromJson(serializer: KSerializer<T>, json: String): T {
        return Json.decodeFromString(serializer, json)
    }
}