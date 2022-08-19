package com.robertconstantindinescu.woutapp.feature_posts.domain.use_case

import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.feature_posts.domain.repository.PostRepository

class ToggleSubscribtionUseCase(private val repository: PostRepository) {

    suspend operator fun invoke(postId: String, isUserSubscribed: Boolean): DefaultApiResource {
        return if (isUserSubscribed) {
            repository.unsubscribeUser(postId)
        } else repository.subscribeUser(postId)
    }
}