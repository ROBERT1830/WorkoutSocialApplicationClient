package com.robertconstantindinescu.woutapp.feature_posts.domain.use_case

import com.robertconstantindinescu.woutapp.feature_posts.domain.model.PostDM
import com.robertconstantindinescu.woutapp.feature_posts.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetAllFavoritesPostsUseCase(private val repository: PostRepository) {
    operator fun invoke(page: Int = 0, offset: Int = 15): Flow<List<PostDM>> =
        repository.getAllFavoritePosts(page, offset)
}