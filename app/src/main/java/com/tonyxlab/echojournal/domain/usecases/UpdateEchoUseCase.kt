package com.tonyxlab.echojournal.domain.usecases

import com.tonyxlab.echojournal.domain.model.Echo
import com.tonyxlab.echojournal.domain.repository.EchoRepository
import com.tonyxlab.echojournal.utils.Resource
import javax.inject.Inject

class UpdateEchoUseCase @Inject constructor(private val repository: EchoRepository) {
    suspend operator fun invoke(echo: Echo):Resource<Boolean>{

        if (echo.id.isBlank()){


            return  Resource.Error(Exception("Error, Please provide a valid Echo item"))
        }
        return repository.updateEcho(echo = echo)

    }
}