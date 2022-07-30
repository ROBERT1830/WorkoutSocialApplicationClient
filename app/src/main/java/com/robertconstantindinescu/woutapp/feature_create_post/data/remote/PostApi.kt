package com.robertconstantindinescu.woutapp.feature_create_post.data.remote

import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PostApi {
    @Multipart
    @POST("/api/post/create")
    suspend fun createPost(
        @Part postData: MultipartBody.Part,
        @Part postImage: MultipartBody.Part
    ): Response<Unit>

    companion object {
        const val POST_BASE_URL = "http://10.0.2.2:8001/"
    }
}