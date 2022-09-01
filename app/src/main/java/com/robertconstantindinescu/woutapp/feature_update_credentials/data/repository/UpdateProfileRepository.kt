package com.robertconstantindinescu.woutapp.feature_update_credentials.data.repository

import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.callApi
import com.robertconstantindinescu.woutapp.feature_update_credentials.data.mapper.toUpdateCredentialDM
import com.robertconstantindinescu.woutapp.feature_update_credentials.data.remote.UpdateCredentialApi
import com.robertconstantindinescu.woutapp.feature_update_credentials.data.remote.dto.request.UpdateCredentialDto
import com.robertconstantindinescu.woutapp.feature_update_credentials.domain.model.UpdateCredentialDM
import com.robertconstantindinescu.woutapp.feature_update_credentials.domain.repository.UpdateProfileRepository

class UpdateProfileRepository(private val api: UpdateCredentialApi): UpdateProfileRepository {

    override suspend fun getCurrentUserCredentials(): Resource<UpdateCredentialDM?> {
        return callApi {
            api.getUserCredentials()
        }.mapApiResponse { it.toUpdateCredentialDM()}
    }

    override suspend fun updateUserCredentials(username: String, email: String): DefaultApiResource {
        return callApi {
            api.updateUserCretentials(
                UpdateCredentialDto(username, email)
            )
        }.mapApiResponse { it }
    }
}