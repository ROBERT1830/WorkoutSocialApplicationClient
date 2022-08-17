package com.robertconstantindinescu.woutapp.feature_posts.domain.repository

import androidx.paging.PagingData
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.feature_posts.domain.model.PostDM
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun getAllPosts(page: Int = 0, pageSize: Int): Resource<List<PostDM>>
    suspend fun getAllCurrentUserPosts(page: Int = 0, pageSize: Int): Resource<List<PostDM>>

    fun getAllFavoritePosts(page: Int, offset: Int): Flow<List<PostDM>>
    suspend fun insertPostIntoFavorites(postDM: PostDM)
    suspend fun deletePostFromFavorites(postDM: PostDM)
}