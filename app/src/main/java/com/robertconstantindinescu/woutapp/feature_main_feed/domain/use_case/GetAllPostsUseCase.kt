package com.robertconstantindinescu.woutapp.feature_main_feed.domain.use_case

import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.feature_main_feed.domain.model.MainFeedPostDM
import com.robertconstantindinescu.woutapp.feature_main_feed.domain.repository.MainFeedRepository
import com.robertconstantindinescu.woutapp.feature_main_feed.util.MainFeedConstants.DEFAULT_PAGE_SIZE

class GetAllPostsUseCase(private val repository: MainFeedRepository) {

    suspend operator fun invoke(page: Int, pageSize: Int = DEFAULT_PAGE_SIZE):
            Resource<List<MainFeedPostDM>> = repository.getAllPosts(page, pageSize)
}