package com.tonyxlab.echojournal.data.repository



import com.tonyxlab.echojournal.data.local.entity.TopicEntity
import com.tonyxlab.echojournal.data.local.mappers.toDomainModel
import com.tonyxlab.echojournal.data.local.mappers.toEchoWithTopics
import com.tonyxlab.echojournal.data.local.mappers.toEntityModel
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
        return dao.getEchoesWithTopics().map { echoWithTopicsList ->
            echoWithTopicsList.map { echoWithTopics ->
                echoWithTopics.toDomainModel()
            }
        }
    }

    override fun getTopics(): Flow<List<String>> {
      return dao.getTopics().map { it.map (TopicEntity::topic) }
    }

    override suspend fun insertEchoWithTopics(echo: Echo): Resource<Boolean> {
        return withContext(dispatchers.io) {
            return@withContext try {

                val existingTopics = dao.getTopicsByEchoId(echo.id)
                val echoWithTopics = echo.toEchoWithTopics(existingTopics                                                                                )

                dao.insertEchoWithTopics(echoWithTopics)
                Resource.Success(true)

            } catch (e: Exception) {

                Resource.Error(e)

            }
        }
    }

    override suspend fun getEchoById(id: String): Resource<Echo> {

        return withContext(dispatchers.io) {

            return@withContext try {
                dao.getEchoWithTopicsById(id = id)?.let {

                    Resource.Success(it.toDomainModel())

                } ?: Resource.Error(KotlinNullPointerException())

            } catch (e: Exception) {

                Resource.Error(exception = e)

            }
        }
    }

    override suspend fun updateEcho(echo: Echo): Resource<Boolean> {

        return withContext(dispatchers.io) {

            return@withContext try {

                val existingTopics = dao.getTopicsByEchoId(echo.id)
                dao.updateEchoWithTopics(echo.toEchoWithTopics(existingTopics))

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