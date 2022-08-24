package com.robertconstantindinescu.woutapp.feature_posts.domain.use_case

import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.feature_posts.domain.model.PostDM
import com.robertconstantindinescu.woutapp.feature_posts.domain.repository.PostRepository

class GetPostDetailsUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(postId: String): Resource<PostDM> {
        return repository.getPostDetails(postId)
    }
}