package com.robertconstantindinescu.woutapp.feature_authentication.data.repository


import android.content.SharedPreferences
import android.net.Uri
import androidx.core.net.toFile
import com.google.gson.Gson
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.KEY_JWT_TOKEN
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.KEY_USER_ID
import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.core.util.callApi
import com.robertconstantindinescu.woutapp.feature_authentication.data.mapper.toAuthModel
import com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.AuthApi
import com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.request.LoginRequestDto
import com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.request.SignUpRequestDto
import com.robertconstantindinescu.woutapp.feature_authentication.domain.repository.AuthRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : AuthRepository {

    override suspend fun signUpUser(
        profileImage: Uri,
        name: String,
        email: String,
        password: String
    ): DefaultApiResource {

        val profileImageFile = profileImage.toFile()

        val response = callApi {
            api.signUpUser(
                profileData = MultipartBody.Part.createFormData(
                    "profile_data",
                    value = gson.toJson(SignUpRequestDto(
                        name = name,
                        email = email,
                        password = password
                    ))
                ),
                profileImage = MultipartBody.Part.createFormData(
                    name = "profile_image",
                    filename = profileImageFile.name,
                    body = profileImageFile.asRequestBody()
                )
            )
        }
       return when(response.successful) {
            true -> {
                Resource.Success<Unit>()
            }
            false -> {
                response.message?.let {
                    Resource.Error(UiText.DynamicString(response.message))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        }
    }

    override suspend fun signInUser(email: String, password: String): DefaultApiResource {

        val response = callApi {
            api.signInUser(
                LoginRequestDto(
                    email = email,
                    password = password
                )
            )
        }
        return when(response.successful) {
            true -> {
                response.data?.toAuthModel().also {
                    sharedPreferences.edit()
                        .putString(KEY_JWT_TOKEN, it?.token)
                        .putString(KEY_USER_ID, it?.userId)
                        .apply()
                }
                Resource.Success<Unit>()
            }
            false -> {
                response.message?.let { serverMsg ->
                    Resource.Error(UiText.DynamicString(serverMsg))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
//            is Resource.Success -> {
//                response.data?.toAuthModel().also {
//                    sharedPreferences.edit()
//                        .putString(KEY_JWT_TOKEN, it?.token)
//                        .putString(KEY_USER_ID, it?.userId)
//                        .apply()
////                 Resource.Success(response.data?.toAuthModel().also {
////                     sharedPreferences.edit()
////                         .putString(KEY_JWT_TOKEN, it?.token)
////                         .putString(KEY_USER_ID, it?.userId)
////                         .apply()
////                 })
//                }
//                Resource.Success<Unit>()
//            }
//            is Resource.Error -> {
//                response.text.apply {
//
//                }
//                Resource.Error(UiText.DynamicString(response.text))            }
        }
    }

    override suspend fun authenticate(): DefaultApiResource {

        val response = callApi {
            api.authenticate()
        }
        return when(response.successful) {
            true -> {
                Resource.Success<Unit>()
            }
            false -> {
                response.message?.let {
                    Resource.Error(UiText.DynamicString(response.message))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        }


    }
}