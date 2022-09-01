package com.robertconstantindinescu.woutapp.feature_posts.data.repository

import android.util.Log
import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.callApi
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.local.entities.FavoritesPostDao
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.remote.PostApi
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.remote.request.PostIdRequest
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.remote.request.SubscribtionRequest
import com.robertconstantindinescu.woutapp.feature_posts.data.mapper.toFavoritePostEntity
import com.robertconstantindinescu.woutapp.feature_posts.data.mapper.toPostDM
import com.robertconstantindinescu.woutapp.feature_posts.domain.model.PostDM
import com.robertconstantindinescu.woutapp.feature_posts.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostRepositoryImpl(
    private val api: PostApi,
    private val local: FavoritesPostDao
): PostRepository {
    /*ownUserId: String, from token server side*/
    override suspend fun getAllPosts(page: Int, pageSize: Int): Resource<List<PostDM>> {

        return callApi {
            api.getAllPosts(page, pageSize)
        }.mapApiResponse { posts ->
            posts.map { it.toPostDM() }
        }
    }

    override suspend fun getAllCurrentUserPosts(
        page: Int,
        pageSize: Int
    ): Resource<List<PostDM>> {
        return callApi {
            api.getAllCurrentUserPosts(page, pageSize)
        }.mapApiResponse { posts -> posts.map { it.toPostDM() } }
    }

    override suspend fun deletePostFromRemote(postId: String): DefaultApiResource {
        Log.d("postid", postId)
        return callApi {
            api.deletePost(PostIdRequest(postId))
        }.mapApiResponse { it }
    }

    override suspend fun getPostDetails(postId: String): Resource<PostDM> {
        return callApi {
            api.getPostDetails(postId)
        }.mapApiResponse { post -> post.toPostDM() }
    }

    override fun getAllFavoritePosts(page: Int, offset: Int): Flow<List<PostDM>> {
        return  local.getAllFavoritesPosts(offset = page * offset).map {
            it.map { post -> post.toPostDM() }
        }
    }

    override suspend fun insertPostIntoFavorites(postDM: PostDM): DefaultApiResource {
        local.insertPostIntoFavorites(postDM.toFavoritePostEntity())
        return callApi {
            api.insertPostToFavorites(PostIdRequest(postId = postDM.postId))
        }.mapApiResponse { it }

    }

    override suspend fun deletePostFromFavorites(postDM: PostDM): DefaultApiResource {
        local.deletePostFromFavorites(postDM.toFavoritePostEntity())
        return callApi {
            api.deletePostToFavorites(PostIdRequest(postId = postDM.postId))
        }.mapApiResponse { it }
    }

    override suspend fun subscribeUser(postId: String): DefaultApiResource {
        return callApi {
            api.subscribeUser(SubscribtionRequest(postId = postId))
        }.mapApiResponse { it }
    }

    override suspend fun unsubscribeUser(postId: String): DefaultApiResource {
        return callApi {
            api.unsubscribeUser(SubscribtionRequest(postId = postId))
        }.mapApiResponse { it }
    }
}