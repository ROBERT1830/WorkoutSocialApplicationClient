package com.robertconstantindinescu.woutapp.feature_posts.domain.use_case

import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.feature_posts.domain.model.PostDM
import com.robertconstantindinescu.woutapp.feature_posts.domain.repository.PostRepository
import com.robertconstantindinescu.woutapp.feature_posts.util.MainFeedConstants.DEFAULT_PAGE_SIZE

class PersonalGetAllPostsUseCase(private val repository: PostRepository) {

    suspend operator fun invoke(page: Int, pageSize: Int = DEFAULT_PAGE_SIZE):
            Resource<List<PostDM>> = repository.getAllCurrentUserPosts(page, pageSize)
}