package com.tonyxlab.echojournal.domain.json

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

interface JsonSerializer {


    fun <T> toJson(serializer: KSerializer<T>, data:T):String

    fun<T>fromJson(serializer:KSerializer<T>, json:String):T



}