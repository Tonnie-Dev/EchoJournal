package com.tonyxlab.echojournal.domain.usecases

import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.domain.repository.EchoRepository
import com.tonyxlab.echojournal.utils.Resource
import javax.inject.Inject

class CreateEchoUseCase @Inject constructor(private val repository: EchoRepository) {

    suspend operator fun invoke(echo: Echo): Resource<Boolean> {

        return repository.insertEchoWithTopics(echo = echo)
    }
}