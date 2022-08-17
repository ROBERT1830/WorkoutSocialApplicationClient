package com.robertconstantindinescu.woutapp.feature_posts.domain.use_case

import com.robertconstantindinescu.woutapp.feature_posts.domain.model.PostDM
import com.robertconstantindinescu.woutapp.feature_posts.domain.repository.PostRepository

class InsertPostIntoFavoritesUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(postDM: PostDM) {
        repository.insertPostIntoFavorites(postDM)
    }
}