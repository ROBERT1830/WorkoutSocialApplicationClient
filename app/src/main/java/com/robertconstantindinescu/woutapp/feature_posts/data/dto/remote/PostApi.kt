package com.robertconstantindinescu.woutapp.feature_posts.data.dto.remote

import com.robertconstantindinescu.woutapp.feature_posts.data.dto.remote.request.PostIdRequest
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.remote.request.SubscribtionRequest
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.remote.response.PostDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PostApi {
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

    @POST("api/subscribe")
    suspend fun subscribeUser(
        @Body request: SubscribtionRequest
    ): Response<Unit>

    @POST("api/unSubscribe")
    suspend fun unsubscribeUser(
        @Body request: SubscribtionRequest
    ): Response<Unit>


    @GET("/api/post/details")
    suspend fun getPostDetails(
        @Query("postId") postId: String
    ):Response<PostDto>

    @POST("/api/post/delete")
    suspend fun deletePost(
        @Body idRequest: PostIdRequest
    ): Response<Unit>

    @POST("/api/post/favorite/insert")
    suspend fun insertPostToFavorites(
        @Body request: PostIdRequest
    ): Response<Unit>

    @POST("/api/post/favorite/delete")
    suspend fun deletePostToFavorites(
        @Body request: PostIdRequest
    ): Response<Unit>

    companion object {
        const val MAIN_FEED_BASE_URL = "http://10.0.2.2:8001/"
    }
}