package com.robertconstantindinescu.woutapp.core.util.subscriptor

import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.model.PostVO

class PostSubscriptorImpl: PostSubscriptor {
    override suspend fun togglePostSubscripton(
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
                        subscriptionsCount = if (currentlySubscribed) (post.subscriptionsCount!! -1).coerceAtLeast(0)
                        else (post.subscriptionsCount!! + 1)
                    )

                }else post
            }
        )
        onRequest(currentlySubscribed).mapResourceData(
            success = { it },
            error = { _, _ ->
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
        )
    }

    override suspend fun togglePostFavorites(
        posts: List<PostVO>,
        postId: String,
        onRequest: suspend (isPostFavorite: Boolean) -> DefaultApiResource,
        onStateUpdate: (List<PostVO>) -> Unit
    ) {
        val currentPost = posts.find { it.postId == postId }
        val isPostFavorite = currentPost?.isAddedToFavorites == true

        onStateUpdate(
            posts.map { post ->
                if (post.postId == postId) {
                    post.copy(
                        isAddedToFavorites = !post.isAddedToFavorites!!
                    )
                }else post
            }
        )
        onRequest(isPostFavorite).mapResourceData(
            success = { it },
            error = { _, _ ->
                onStateUpdate(
                    posts.map { post ->
                        if (post.postId == postId) {
                            post.copy(
                                isAddedToFavorites = isPostFavorite
                            )
                        } else post
                    }
                )
            }
        )
    }
}