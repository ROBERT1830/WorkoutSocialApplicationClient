package com.robertconstantindinescu.woutapp.feature_authentication.domain.repository

import com.robertconstantindinescu.woutapp.core.util.ApiResource
import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.feature_authentication.domain.model.AuthModel

interface AuthRepository {

    suspend fun signUpUser(
        name: String,
        email: String,
        password: String
    ): DefaultApiResource

    suspend fun signInUser(
        email: String,
        password: String
    ): ApiResource<AuthModel>

    suspend fun authenticate(): DefaultApiResource
}