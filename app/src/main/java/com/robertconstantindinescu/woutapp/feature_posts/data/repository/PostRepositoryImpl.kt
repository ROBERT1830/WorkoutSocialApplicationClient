package com.robertconstantindinescu.woutapp.feature_posts.data.repository

import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.core.util.callApi
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.local.entities.FavoritesPostDao
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.remote.PostApi
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

//        return when(response.successful) {
//            true -> {
//                if (response.data?.isNotEmpty() == true) {
//                    Resource.Success(response.data.map { postDto ->
//                        postDto.toPostDM()
//                    })
//                }else Resource.Success(emptyList<PostDM>())
//            }
//            false -> {
//                response.message?.let {
//                    Resource.Error(UiText.DynamicString(response.message))
//                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
//            }
//        }
    }

    override suspend fun getAllCurrentUserPosts(
        page: Int,
        pageSize: Int
    ): Resource<List<PostDM>> {
        return callApi {
            api.getAllCurrentUserPosts(page, pageSize)
        }.mapApiResponse { posts -> posts.map { it.toPostDM() } }
    }

    override suspend fun getPostDetails(postId: String): Resource<PostDM> {
        return callApi {
            api.getPostDetails(postId)
        }.mapApiResponse { post -> post.toPostDM() }

//        return when(response.successful) {
//            true -> {
//                response.data.let {
//                    Resource.Success(it?.data?.toPostDM())
//                }
//            }
//            false -> {
//                response.message?.let {
//                    Resource.Error(UiText.DynamicString(response.message))
//                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
//            }
//        }
    }

    override fun getAllFavoritePosts(page: Int, offset: Int): Flow<List<PostDM>> {
        return  local.getAllFavoritesPosts(offset = page * offset).map {
            it.map { post -> post.toPostDM() }
        }
    }

    override suspend fun insertPostIntoFavorites(postDM: PostDM) {
        local.insertPostIntoFavorites(postDM.toFavoritePostEntity())
    }

    override suspend fun deletePostFromFavorites(postDM: PostDM) {
        local.deletePostFromFavorites(postDM.toFavoritePostEntity())
    }

    override suspend fun subscribeUser(postId: String): DefaultApiResource {
        return callApi {
            api.subscribeUser(SubscribtionRequest(postId = postId))
        }.mapApiResponse { it }

//        return when(response.successful) {
//            true -> {
//                Resource.Success<Unit>()
//            }
//            false -> {
//                response.message?.let { serverMsg ->
//                    Resource.Error(UiText.DynamicString(serverMsg))
//                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
//            }
//        }
    }

    override suspend fun unsubscribeUser(postId: String): DefaultApiResource {
        return callApi {
            api.unsubscribeUser(SubscribtionRequest(postId = postId))
        }.mapApiResponse { it }

//        return when(response.successful) {
//            true -> {
//                Resource.Success<Unit>()
//            }
//            false -> {
//                response.message?.let { serverMsg ->
//                    Resource.Error(UiText.DynamicString(serverMsg))
//                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
//            }
//        }
    }
}