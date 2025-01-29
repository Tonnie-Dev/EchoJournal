package com.tonyxlab.echojournal.domain.usecases

import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.domain.repository.EchoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEchoesUseCase @Inject constructor(private val repository: EchoRepository) {

    operator fun invoke(): Flow<List<Echo>> {

        return repository.getEchos()
    }
}