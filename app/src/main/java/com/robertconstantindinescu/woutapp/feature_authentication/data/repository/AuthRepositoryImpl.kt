package com.robertconstantindinescu.woutapp.feature_authentication.data.repository


import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.core.util.callApi
import com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.AuthApi
import com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.request.SignUpRequest
import com.robertconstantindinescu.woutapp.feature_authentication.domain.repository.AuthRepository

class AuthRepositoryImpl(
    val api: AuthApi
) : AuthRepository {

    override suspend fun signUpUser(
        name: String,
        email: String,
        password: String
    ): DefaultApiResource {

        return callApi {
            api.signUpUser(
                SignUpRequest(
                    name = name,
                    email = email,
                    password = password
                )
            )
        }

    }
}