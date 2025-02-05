package com.tonyxlab.echojournal.domain.repository

import com.tonyxlab.echojournal.data.database.entity.EchoWithTopics
import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.utils.Resource
import kotlinx.coroutines.flow.Flow

interface EchoRepository {

    fun getEchos():Flow<List<Echo>>
    //fun getSavedTopics():Flow<List<String>>
    suspend fun getEchoById(id: String): Resource<Echo>
    //suspend fun createEcho(echo: Echo): Resource<Boolean>
    suspend fun updateEcho(echo: Echo): Resource<Boolean>
    suspend fun deleteEcho(echo: Echo): Resource<Boolean>
    suspend fun insertEchoWithTopics(echo: Echo):Resource<Boolean>
}