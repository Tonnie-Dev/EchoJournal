package com.tonyxlab.echojournal.domain.repository

import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.utils.Resource
import kotlinx.coroutines.flow.Flow

interface EchoRepository {

    fun getEchos(): Flow<List<Echo>>
    suspend fun getEchoById(id: String): Resource<Echo>
    suspend fun createEcho(echo: Echo): Resource<Boolean>
    suspend fun updateEcho(echo: Echo): Resource<Boolean>
    suspend fun deleteEcho(echo: Echo): Resource<Boolean>

}