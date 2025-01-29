package com.tonyxlab.echojournal.domain.usecases

import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.domain.repository.EchoRepository
import com.tonyxlab.echojournal.utils.Resource
import javax.inject.Inject

class GetEchoByIdUseCase @Inject constructor(private val repository: EchoRepository) {

    suspend operator fun invoke(id: String?): Resource<Echo> {
        
        if (id.isNullOrBlank()) {

            return Resource.Error(exception = Exception("Please provide a valid Id"))
        }
        return repository.getEchoById(id = id)
    }
}