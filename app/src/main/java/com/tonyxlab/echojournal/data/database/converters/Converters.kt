package com.tonyxlab.echojournal.data.database.converters

import androidx.room.ProvidedTypeConverter
import com.tonyxlab.echojournal.domain.json.JsonSerializer
import javax.inject.Inject

@ProvidedTypeConverter
class Converters @Inject constructor( private val serializer: JsonSerializer){


}