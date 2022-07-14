package com.robertconstantindinescu.woutapp.feature_authentication.data.repository


import com.robertconstantindinescu.woutapp.core.util.ApiResource
import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.core.util.callApi
import com.robertconstantindinescu.woutapp.feature_authentication.data.mapper.toAuthModel
import com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.AuthApi
import com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.request.LoginRequestDto
import com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.request.SignUpRequestDto
import com.robertconstantindinescu.woutapp.feature_authentication.domain.model.AuthModel
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
                SignUpRequestDto(
                    name = name,
                    email = email,
                    password = password
                )
            )
        }
    }

    override suspend fun signInUser(email: String, password: String): ApiResource<AuthModel> {

        val response = callApi {
            api.signInUser(
                LoginRequestDto(
                    email = email,
                    password = password
                )
            )
        }
        // TODO: SAVE THE TOKEN IN SHARED PREFERENCES
        return when(response) {
            is ApiResource.Success -> {
                 ApiResource.Success(response.data?.toAuthModel())
            }
            is ApiResource.Error -> {
                ApiResource.Error(text = response.text)
            }
        }
    }
}