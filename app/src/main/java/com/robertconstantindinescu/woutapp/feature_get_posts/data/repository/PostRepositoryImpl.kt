package com.robertconstantindinescu.woutapp.feature_get_posts.data.repository

import android.content.SharedPreferences
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.util.CoreConstants
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.KEY_USER_ID
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.core.util.callApi
import com.robertconstantindinescu.woutapp.feature_get_posts.data.dto.remote.MainFeedApi
import com.robertconstantindinescu.woutapp.feature_get_posts.data.mapper.toMainFeedPostDM
import com.robertconstantindinescu.woutapp.feature_get_posts.domain.model.PostDM
import com.robertconstantindinescu.woutapp.feature_get_posts.domain.repository.PostRepository

class PostRepositoryImpl(
    private val api: MainFeedApi
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
                        postDto.toMainFeedPostDM()
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
                        postDto.toMainFeedPostDM()
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
}