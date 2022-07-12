package com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto

import com.robertconstantindinescu.woutapp.core.data.response.BasicApiResponse
import com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.request.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/user/create")
    suspend fun signUpUser(
        @Body request: SignUpRequest
    ): Response<Unit>

    companion object {
        const val AUT_BASE_URL = "http://10.0.2.2:8001/"
    }

}