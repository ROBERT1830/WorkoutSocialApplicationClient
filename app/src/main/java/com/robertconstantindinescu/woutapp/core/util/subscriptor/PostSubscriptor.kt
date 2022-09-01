package com.robertconstantindinescu.woutapp.core.util.subscriptor

import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.model.PostVO

interface PostSubscriptor {
    suspend fun togglePostSubscripton(
        posts: List<PostVO>,
        postId: String,
        onRequest: suspend (isUserSubscribed: Boolean) -> DefaultApiResource,
        onStateUpdate: (List<PostVO>) -> Unit
    )
    suspend fun togglePostFavorites(
        posts: List<PostVO>,
        postId: String,
        onRequest: suspend (isPostFavorite: Boolean) -> DefaultApiResource,
        onStateUpdate: (List<PostVO>) -> Unit
    )
}