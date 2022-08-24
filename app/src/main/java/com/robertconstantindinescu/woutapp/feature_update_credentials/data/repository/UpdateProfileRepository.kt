package com.robertconstantindinescu.woutapp.feature_update_credentials.data.repository

import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.core.util.callApi
import com.robertconstantindinescu.woutapp.feature_update_credentials.data.mapper.toUpdateCredentialDM
import com.robertconstantindinescu.woutapp.feature_update_credentials.data.remote.UpdateCredentialApi
import com.robertconstantindinescu.woutapp.feature_update_credentials.data.remote.dto.request.UpdateCredentialDto
import com.robertconstantindinescu.woutapp.feature_update_credentials.domain.model.UpdateCredentialDM
import com.robertconstantindinescu.woutapp.feature_update_credentials.domain.repository.UpdateProfileRepository

class UpdateProfileRepository(private val api: UpdateCredentialApi): UpdateProfileRepository {

    override suspend fun getCurrentUserCredentials(): Resource<UpdateCredentialDM?> {
        val response = callApi {
            api.getUserCredentials()
        }

        return when(response.successful) {
            true -> {
                response.data?.let {
                    Resource.Success(response.data.data?.toUpdateCredentialDM())
                } ?: Resource.Success(null)

            }
            false -> {
                response.message?.let { serverMsg ->
                    Resource.Error(UiText.DynamicString(serverMsg))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        }
    }

    override suspend fun updateUserCredentials(username: String, email: String): DefaultApiResource {
        val response = callApi {
            api.updateUserCretentials(
                UpdateCredentialDto(username, email)
            )
        }

        return when(response.successful) {
            true -> {
                Resource.Success()
            }
            false -> {
                response.message?.let { serverMsg ->
                    Resource.Error(UiText.DynamicString(serverMsg))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        }
    }

    // TODO: DI NAD VIEWMODEL AND BACKEND TO GET OBJECT FOR GETCURRENUsERcaREDETNEIAL
}