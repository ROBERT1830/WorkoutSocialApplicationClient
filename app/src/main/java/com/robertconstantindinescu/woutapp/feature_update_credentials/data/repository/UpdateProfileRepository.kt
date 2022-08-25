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
        return callApi {
            api.getUserCredentials()
        }.mapApiResponse { it.toUpdateCredentialDM()}

//        return when(response.successful) {
//            true -> {
//                response.data?.let {
//                    Resource.Success(response.data.data?.toUpdateCredentialDM())
//                } ?: Resource.Success(null)
//
//            }
//            false -> {
//                response.message?.let { serverMsg ->
//                    Resource.Error(UiText.DynamicString(serverMsg))
//                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
//            }
//        }
    }

    override suspend fun updateUserCredentials(username: String, email: String): DefaultApiResource {
        return callApi {
            api.updateUserCretentials(
                UpdateCredentialDto(username, email)
            )
        }.mapApiResponse { it }

//        return when(response.successful) {
//            true -> {
//                Resource.Success()
//            }
//            false -> {
//                response.message?.let { serverMsg ->
//                    Resource.Error(UiText.DynamicString(serverMsg))
//                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
//            }
//        }
    }

    // TODO: DI NAD VIEWMODEL AND BACKEND TO GET OBJECT FOR GETCURRENUsERcaREDETNEIAL
}