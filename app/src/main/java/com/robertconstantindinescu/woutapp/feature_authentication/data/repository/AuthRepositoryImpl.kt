package com.robertconstantindinescu.woutapp.feature_authentication.data.repository


import android.content.SharedPreferences
import android.net.Uri
import androidx.core.net.toFile
import com.google.gson.Gson
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.util.*
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.KEY_JWT_TOKEN
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.KEY_USER_ID
import com.robertconstantindinescu.woutapp.feature_authentication.data.mapper.toAuthModel
import com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.AuthApi
import com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.request.LoginRequestDto
import com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.request.SignUpRequestDto
import com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.response.AuthResponseDto
import com.robertconstantindinescu.woutapp.feature_authentication.domain.repository.AuthRepository
import com.robertconstantindinescu.woutapp.feature_authentication.presentation.util.AuthConstants.PROFILE_DATA
import com.robertconstantindinescu.woutapp.feature_authentication.presentation.util.AuthConstants.PROFILE_IMAGE
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : AuthRepository {

    //Do not pass the object so that the repo function won't know where the data will go
    override suspend fun signUpUser(
        profileImage: Uri,
        name: String,
        email: String,
        password: String
    ): DefaultApiResource {

        val profileImageFile = profileImage.toFile()

        return callApi {
            api.signUpUser(
                profileData = MultipartBody.Part.createFormData(
                    name = PROFILE_DATA,
                    value = gson.toJson(
                        SignUpRequestDto(
                            name = name,
                            email = email,
                            password = password
                        )
                    )
                ),
                profileImage = MultipartBody.Part.createFormData(
                    name = PROFILE_IMAGE,
                    filename = profileImageFile.name,
                    body = profileImageFile.asRequestBody()
                )
            )
        }.mapApiResponse { it }
    }

    override suspend fun signInUser(email: String, password: String): DefaultApiResource {

        return callApi<AuthResponseDto> {
            api.signInUser(
                LoginRequestDto(
                    email = email,
                    password = password
                )
            )
        }.mapApiResponse { response ->
            response.toAuthModel().also {
                sharedPreferences.edit()
                    .putString(KEY_JWT_TOKEN, it.token)
                    .putString(KEY_USER_ID, it.userId)
                    .apply()
            }
        }

//        return when (response.successful) {
//            true -> {
//                response.data?.toAuthModel().also {
//                    sharedPreferences.edit()
//                        .putString(KEY_JWT_TOKEN, it?.token)
//                        .putString(KEY_USER_ID, it?.userId)
//                        .apply()
//                }
//                Resource.Success<Unit>()
//            }
//            false -> {
//                response.message?.let { serverMsg ->
//                    Resource.Error(UiText.DynamicString(serverMsg))
//                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
//            }
//        }
    }

    override suspend fun authenticate(): DefaultApiResource {

        return callApi {
            api.authenticate()
        }.mapApiResponse { it }
        //return response.mapApiResponse { it }
    }
}