package com.robertconstantindinescu.woutapp.feature_authentication.domain.use_case

import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.feature_authentication.domain.repository.AuthRepository

class InitAuthUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(): DefaultApiResource {
        return repository.authenticate()
    }
}