package com.robertconstantindinescu.woutapp.feature_posts.domain.use_case

import com.robertconstantindinescu.woutapp.feature_posts.domain.model.PostDM
import com.robertconstantindinescu.woutapp.feature_posts.domain.repository.PostRepository

class DeleteFromFavoritesUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(postDM: PostDM) {
        repository.deletePostFromFavorites(postDM)
    }
}