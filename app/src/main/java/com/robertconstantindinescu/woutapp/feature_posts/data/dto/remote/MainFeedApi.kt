package com.robertconstantindinescu.woutapp.feature_posts.data.dto.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainFeedApi {
    @GET("/api/post/getAll")
    suspend fun getAllPosts(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<List<PostDto>>

    @GET("/api/post/current_user/getAll")
    suspend fun getAllCurrentUserPosts(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<List<PostDto>>

    companion object {
        const val MAIN_FEED_BASE_URL = "http://10.0.2.2:8001/"
    }
}