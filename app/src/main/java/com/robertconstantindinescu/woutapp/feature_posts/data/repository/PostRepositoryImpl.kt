package com.robertconstantindinescu.woutapp.feature_posts.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.core.util.callApi
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.local.entities.FavoritesPostDao
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.remote.MainFeedApi
import com.robertconstantindinescu.woutapp.feature_posts.data.mapper.toFavoritePostEntity
import com.robertconstantindinescu.woutapp.feature_posts.data.mapper.toPostDM
import com.robertconstantindinescu.woutapp.feature_posts.domain.model.PostDM
import com.robertconstantindinescu.woutapp.feature_posts.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostRepositoryImpl(
    private val api: MainFeedApi,
    private val local: FavoritesPostDao
): PostRepository {
    /*ownUserId: String, from token server side*/
    override suspend fun getAllPosts(page: Int, pageSize: Int): Resource<List<PostDM>> {

        val response = callApi {
            api.getAllPosts(page, pageSize)
        }

        return when(response.successful) {
            true -> {
                if (response.data?.isNotEmpty() == true) {
                    Resource.Success(response.data.map { postDto ->
                        postDto.toPostDM()
                    })
                }else Resource.Success(emptyList<PostDM>())
            }
            false -> {
                response.message?.let {
                    Resource.Error(UiText.DynamicString(response.message))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        }
    }

    override suspend fun getAllCurrentUserPosts(
        page: Int,
        pageSize: Int
    ): Resource<List<PostDM>> {
        val response = callApi {
            api.getAllCurrentUserPosts(page, pageSize)
        }

        return when(response.successful) {
            true -> {
                if (response.data?.isNotEmpty() == true) {
                    Resource.Success(response.data.map { postDto ->
                        postDto.toPostDM()
                    })
                }else Resource.Success(emptyList<PostDM>())
            }
            false -> {
                response.message?.let {
                    Resource.Error(UiText.DynamicString(response.message))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        }
    }

    override fun getAllFavoritePosts(page: Int, offset: Int): Flow<List<PostDM>> {
        return local.getAllFavoritesPosts(page, page * offset).map {
            it.map { post -> post.toPostDM() }
        }

    }
        //val pagingSourceFactory = {local.getAllFavoritesPosts(page, offset) }
//        return Pager(
//            config = PagingConfig(pageSize = 15),
//            pagingSourceFactory = {
//
//            }
//        ).flow
//    }
//        local.getAllFavoritesPosts().map {
//            it.map { post ->
//                post.toPostDM()
//            }
//        }

    override suspend fun insertPostIntoFavorites(postDM: PostDM) {
        local.insertPostIntoFavorites(postDM.toFavoritePostEntity())
    }

    override suspend fun deletePostFromFavorites(postDM: PostDM) {
        local.deletePostFromFavorites(postDM.toFavoritePostEntity())
    }
}