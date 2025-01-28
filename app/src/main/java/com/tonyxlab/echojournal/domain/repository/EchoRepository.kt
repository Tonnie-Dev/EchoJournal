package com.tonyxlab.echojournal.domain.repository

import com.tonyxlab.echojournal.domain.model.Echo
import kotlinx.coroutines.flow.Flow

interface EchoRepository {

    fun getEchos():Flow<List<Echo>>
    fun getEchoById(id:String):Echo
}