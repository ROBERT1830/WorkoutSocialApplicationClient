package com.robertconstantindinescu.woutapp.feature_main_feed.presentation.model

data class MainFeedPostVO(
    val postId: String,
    val userId: String,
    val userName: String,
    val imageUrl: String,
    val sportType: String,
    val description: String,
    val location: String,
    val subscriptionsCount: Long = 0,
    val likeCount: Long = 0,
    val isAddedToFavorites : Boolean
)