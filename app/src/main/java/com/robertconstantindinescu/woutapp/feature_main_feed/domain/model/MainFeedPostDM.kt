package com.robertconstantindinescu.woutapp.feature_main_feed.domain.model

data class MainFeedPostDM(
    val postId: String?,
    val userId: String?,
    val userName: String?,
    val imageUrl: String?,
    val profileImage: String?,
    val sportType: String?,
    val description: String?,
    val location: String?,
    val subscriptionsCount: Long? = 0,
    val isAddedToFavorites : Boolean?,
    val isUserSubscribed: Boolean?
)
