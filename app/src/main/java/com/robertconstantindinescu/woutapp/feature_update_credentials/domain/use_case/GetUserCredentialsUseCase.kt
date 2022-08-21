package com.robertconstantindinescu.woutapp.feature_update_credentials.domain.use_case

import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.feature_update_credentials.domain.model.UpdateCredentialDM
import com.robertconstantindinescu.woutapp.feature_update_credentials.domain.repository.UpdateProfileRepository

class GetUserCredentialsUseCase(private val repository: UpdateProfileRepository) {

    suspend operator fun invoke(): Resource<UpdateCredentialDM?> =
        repository.getCurrentUserCredentials()

}