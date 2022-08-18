package com.robertconstantindinescu.woutapp.core.util.subscriptor

import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.feature_posts.presentation.main_feed_screen.model.PostVO

class PostSubscriptorImpl: PostSubscriptor {
    override suspend fun togglePostSubscriptor(
        posts: List<PostVO>,
        postId: String,
        onRequest: suspend (isUserSubscribed: Boolean) -> DefaultApiResource,
        onStateUpdate: (List<PostVO>) -> Unit
    ) {
        val currentPost = posts.find { it.postId == postId }
        val currentlySubscribed = currentPost?.isUserSubscribed == true
        val currentSubscriptionsCount = currentPost?.subscriptionsCount ?: 0

        onStateUpdate(
            posts.map { post ->
                if (post.postId == postId) {
                    post.copy(
                        isUserSubscribed = !post.isUserSubscribed!!,
                        subscriptionsCount = if (currentlySubscribed) post.subscriptionsCount!! -1
                        else (post.subscriptionsCount!! - 1).coerceAtLeast(0)
                    )
                }else post
            }
        )

        when (onRequest(currentlySubscribed)) {
            is Resource.Success -> Unit
            is Resource.Error -> {
                onStateUpdate(
                    posts.map { post ->
                        if (post.postId == postId) {
                            post.copy(
                                isUserSubscribed = currentlySubscribed,
                                subscriptionsCount = currentSubscriptionsCount
                            )
                        } else post
                    }
                )
            }
        }
    }
}