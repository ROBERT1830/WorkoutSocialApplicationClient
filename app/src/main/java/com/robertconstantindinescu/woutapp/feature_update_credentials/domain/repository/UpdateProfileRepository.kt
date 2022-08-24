package com.robertconstantindinescu.woutapp.feature_update_credentials.domain.repository

import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.feature_update_credentials.data.remote.dto.request.UpdateCredentialDto
import com.robertconstantindinescu.woutapp.feature_update_credentials.domain.model.UpdateCredentialDM

interface UpdateProfileRepository {
    suspend fun getCurrentUserCredentials(): Resource<UpdateCredentialDM?>
    suspend fun updateUserCredentials(username: String, email: String): DefaultApiResource
}