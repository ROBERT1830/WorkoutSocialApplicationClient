package com.robertconstantindinescu.woutapp.feature_get_posts.domain.use_case

import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.feature_get_posts.domain.model.PostDM
import com.robertconstantindinescu.woutapp.feature_get_posts.domain.repository.PostRepository
import com.robertconstantindinescu.woutapp.feature_get_posts.util.MainFeedConstants.DEFAULT_PAGE_SIZE

class MainFeedGetAllPostsUseCase(private val repository: PostRepository) {

    suspend operator fun invoke(page: Int, pageSize: Int = DEFAULT_PAGE_SIZE):
            Resource<List<PostDM>> = repository.getAllPosts(page, pageSize)
}