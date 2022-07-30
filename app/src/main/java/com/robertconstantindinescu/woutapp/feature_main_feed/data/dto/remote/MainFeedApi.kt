package com.robertconstantindinescu.woutapp.feature_main_feed.data.dto.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainFeedApi {
    @GET("/api/post/getAll")
    suspend fun getAllPosts(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<List<PostRequest>>
}