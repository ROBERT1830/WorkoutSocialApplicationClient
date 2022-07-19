package com.robertconstantindinescu.woutapp.feature_authentication.data.repository


import android.content.SharedPreferences
import com.robertconstantindinescu.woutapp.core.util.ApiResource
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.KEY_JWT_TOKEN
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.KEY_USER_ID
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
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences
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
                 ApiResource.Success(response.data?.toAuthModel().also {
                     sharedPreferences.edit()
                         .putString(KEY_JWT_TOKEN, it?.token)
                         .putString(KEY_USER_ID, it?.userId)
                         .apply()
                 })
            }
            is ApiResource.Error -> {
                ApiResource.Error(text = response.text)
            }
        }
    }

    override suspend fun authenticate(): DefaultApiResource {

        return callApi {
            api.authenticate()
        }

    }
}