package com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto

import com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.request.LoginRequestDto
import com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.response.AuthResponseDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {

    @Multipart
    @POST("/api/user/create")
    suspend fun signUpUser(
        @Part profileData: MultipartBody.Part,
        @Part profileImage: MultipartBody.Part
    ): Response<Unit>


    @POST("/api/user/signin")
    suspend fun signInUser(
        @Body requestDto: LoginRequestDto
    ): Response<AuthResponseDto>

    @GET("/api/user/authenticate")
    suspend fun authenticate(): Response<Unit>

    companion object {
        const val AUT_BASE_URL = "http://10.0.2.2:8001/"
    }

}