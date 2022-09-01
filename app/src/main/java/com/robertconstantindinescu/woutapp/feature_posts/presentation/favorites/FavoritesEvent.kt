package com.robertconstantindinescu.woutapp.feature_posts.presentation.favorites

import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.model.PostVO

sealed class FavoritesEvent {
    data class onDeleteFavoritePost(val post: PostVO) : FavoritesEvent()
    data class OnToggleSubscription(val postId: String) : FavoritesEvent()
}
