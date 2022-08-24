package com.robertconstantindinescu.woutapp.feature_update_credentials.data.remote

import com.robertconstantindinescu.woutapp.core.data.response.ApiResponse
import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.feature_update_credentials.data.remote.dto.request.UpdateCredentialDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UpdateCredentialApi {
    @GET("/api/user/credentials")
    suspend fun getUserCredentials(): Response<ApiResponse<UpdateCredentialDto>>

    @POST("/api/user/update")
    suspend fun updateUserCretentials(
        @Body request: UpdateCredentialDto
    ): Response<Unit>


    companion object {
        const val UPDATE_CREDENTIALS_BASE_URL = "http://10.0.2.2:8001/"
    }
}