package com.tonyxlab.echojournal.data.repository

import com.tonyxlab.echojournal.data.database.dao.EchoDao
import com.tonyxlab.echojournal.data.mappers.toDomainModel
import com.tonyxlab.echojournal.data.mappers.toEntityModel
import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.domain.repository.EchoRepository
import com.tonyxlab.echojournal.utils.AppCoroutineDispatchers
import com.tonyxlab.echojournal.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EchoRepositoryImpl @Inject constructor(
    private val dao: EchoDao,
    private val dispatchers: AppCoroutineDispatchers
) : EchoRepository {
    override fun getEchos(): Flow<List<Echo>> {

        return dao.getEchos().map {
            it.map { entity -> entity.toDomainModel() }
        }
    }

    override suspend fun getEchoById(id: String): Resource<Echo> {

        return withContext(dispatchers.io) {

            return@withContext try {
                dao.getEchoById(id = id)?.let {

                    Resource.Success(it.toDomainModel())
                } ?: Resource.Error(KotlinNullPointerException())
            } catch (e: Exception) {
                Resource.Error(exception = e)
            }
        }

    }

    override suspend fun createEcho(echo: Echo): Resource<Boolean> {

        return withContext(dispatchers.io) {

            return@withContext try {
                val result = dao.insert(echo.toEntityModel())
                Resource.Success(result != -1L)
            } catch (e: Exception) {

                Resource.Error(e)
            }
        }

    }

    override suspend fun updateEcho(echo: Echo): Resource<Boolean> {

        return withContext(dispatchers.io) {

            return@withContext try {
                dao.update(echo.toEntityModel())
                Resource.Success(true)
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }
    }

    override suspend fun deleteEcho(echo: Echo): Resource<Boolean> {
        return withContext(dispatchers.io) {
            return@withContext try {
                val result = dao.delete(echo.toEntityModel())
                Resource.Success(result != -1)
            } catch (e: Exception) {

                Resource.Error(e)
            }

        }
    }
}