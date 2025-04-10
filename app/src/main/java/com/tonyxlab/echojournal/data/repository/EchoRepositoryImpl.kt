package com.tonyxlab.echojournal.data.repository

import com.tonyxlab.echojournal.data.local.dao.EchoDao
import com.tonyxlab.echojournal.data.local.mappers.toEchoEntity
import com.tonyxlab.echojournal.data.local.mappers.toEchoesList
import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.domain.repository.EchoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EchoRepositoryImpl
    @Inject
    constructor(private val dao: EchoDao) : EchoRepository {
        override fun getEchos(): Flow<List<Echo>> {
            return dao.getEchoes().map { it.toEchoesList() }
        }

        override suspend fun upsertEcho(echo: Echo) {
            dao.upsert(echo.toEchoEntity())
        }

        override suspend fun deleteEcho(echo: Echo) {
            dao.delete(echo.toEchoEntity())
        }
    }