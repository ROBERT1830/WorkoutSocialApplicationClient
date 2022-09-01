package com.robertconstantindinescu.woutapp.feature_posts.domain.repository

import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.feature_posts.domain.model.PostDM
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun getAllPosts(page: Int = 0, pageSize: Int): Resource<List<PostDM>>
    suspend fun getAllCurrentUserPosts(page: Int = 0, pageSize: Int): Resource<List<PostDM>>
    suspend fun getPostDetails(postId: String): Resource<PostDM>
    suspend fun deletePostFromRemote(postId: String): DefaultApiResource

    fun getAllFavoritePosts(page: Int, offset: Int): Flow<List<PostDM>>
    suspend fun insertPostIntoFavorites(postDM: PostDM): DefaultApiResource
    suspend fun deletePostFromFavorites(postDM: PostDM): DefaultApiResource

    suspend fun subscribeUser(postId: String): DefaultApiResource
    suspend fun unsubscribeUser(postId: String): DefaultApiResource
}