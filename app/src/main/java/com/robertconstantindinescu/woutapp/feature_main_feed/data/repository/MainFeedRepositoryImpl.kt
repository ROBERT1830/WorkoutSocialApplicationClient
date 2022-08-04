package com.robertconstantindinescu.woutapp.feature_main_feed.data.repository

import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.core.util.callApi
import com.robertconstantindinescu.woutapp.feature_main_feed.data.dto.remote.MainFeedApi
import com.robertconstantindinescu.woutapp.feature_main_feed.data.mapper.toMainFeedPostModel
import com.robertconstantindinescu.woutapp.feature_main_feed.domain.model.MainFeedPostDM
import com.robertconstantindinescu.woutapp.feature_main_feed.domain.repository.MainFeedRepository

class MainFeedRepositoryImpl(
    private val api: MainFeedApi
): MainFeedRepository {
    /*ownUserId: String, from token server side*/
    override suspend fun getAllPosts(page: Int, pageSize: Int): Resource<List<MainFeedPostDM>> {
        val response = callApi {
            api.getAllPosts(page, pageSize)
        }

        return when(response.successful) {
            true -> {
                if (response.data?.isNotEmpty() == true) {
                    Resource.Success(response.data.map { postDto ->
                        postDto.toMainFeedPostModel()
                    })
                }else Resource.Success(emptyList<MainFeedPostDM>())
            }
            false -> {
                response.message?.let {
                    Resource.Error(UiText.DynamicString(response.message))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        }
    }
}