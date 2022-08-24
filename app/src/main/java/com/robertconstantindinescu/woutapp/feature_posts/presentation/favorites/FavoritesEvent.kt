package com.robertconstantindinescu.woutapp.feature_posts.presentation.favorites

import com.robertconstantindinescu.woutapp.feature_posts.presentation.main_feed_screen.MainFeedEvent
import com.robertconstantindinescu.woutapp.feature_posts.presentation.main_feed_screen.model.PostVO

sealed class FavoritesEvent {
    data class onDeleteFavoritePost(val post: PostVO): FavoritesEvent()
    data class OnToggleSubscription(val postId: String): FavoritesEvent()
    // TODO: share and subscribe from favoritres

}
