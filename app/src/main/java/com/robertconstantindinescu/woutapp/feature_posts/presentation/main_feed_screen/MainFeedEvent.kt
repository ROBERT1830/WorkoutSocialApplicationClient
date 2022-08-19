package com.robertconstantindinescu.woutapp.feature_posts.presentation.main_feed_screen

import com.robertconstantindinescu.woutapp.feature_posts.presentation.main_feed_screen.model.PostVO

sealed class MainFeedEvent {
    data class OnFavoriteClick(val post: PostVO): MainFeedEvent()
    data class OnToggleSubscription(val postId: String): MainFeedEvent()
    //Share
    //Suscribe to evenrt
}
