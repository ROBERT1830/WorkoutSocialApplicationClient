package com.robertconstantindinescu.woutapp.core.util.subscriptor

import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.feature_posts.presentation.main_feed_screen.model.PostVO

interface PostSubscriptor {
    suspend fun togglePostSubscriptor(
        posts: List<PostVO>,
        postId: String,
        onRequest: suspend (isUserSubscribed: Boolean) -> DefaultApiResource,
        onStateUpdate: (List<PostVO>) -> Unit
    )
}