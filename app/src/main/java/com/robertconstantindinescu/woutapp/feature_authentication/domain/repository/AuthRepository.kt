package com.robertconstantindinescu.woutapp.feature_authentication.domain.repository

import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource

interface AuthRepository {

    suspend fun signUpUser(
        name: String,
        email: String,
        password: String
    ): DefaultApiResource

    suspend fun signInUser(
        email: String,
        password: String
    ): DefaultApiResource

    suspend fun authenticate(): DefaultApiResource
}