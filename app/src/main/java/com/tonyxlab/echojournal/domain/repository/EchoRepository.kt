package com.tonyxlab.echojournal.domain.repository

import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.utils.Resource
import kotlinx.coroutines.flow.Flow

interface EchoRepository {

    fun getEchos(): Flow<List<Echo>>
    fun getEchoById(id: String): Resource<Echo>
    fun createEcho(echo: Echo): Resource<Boolean>
    fun updateEcho(echo: Echo): Resource<Boolean>
    fun deleteEcho(echo: Echo): Resource<Boolean>

}