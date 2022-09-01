package com.robertconstantindinescu.woutapp.feature_posts.domain.use_case

import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.feature_posts.domain.model.PostDM
import com.robertconstantindinescu.woutapp.feature_posts.domain.repository.PostRepository

class InsertPostIntoFavoritesUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(postDM: PostDM, isPostLiked: Boolean): DefaultApiResource {
       return if (isPostLiked) repository.deletePostFromFavorites(postDM)
       else repository.insertPostIntoFavorites(postDM)
    }
}