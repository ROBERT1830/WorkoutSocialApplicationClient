package com.robertconstantindinescu.woutapp.feature_posts.domain.use_case

import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.feature_posts.domain.repository.PostRepository

class DeletePostFromRemoteUseCase(private val repository: PostRepository) {

    suspend operator fun invoke(postId: String): DefaultApiResource {
        return repository.deletePostFromRemote(postId)
    }
}