package com.robertconstantindinescu.woutapp.feature_posts.presentation.common.model

data class PostVO(
    val postId: String,
    val userId: String?,
    val userName: String?,
    val imageUrl: String?,
    val profileImage: String?,
    val sportType: String?,
    val description: String?,
    val location: String?,
    val subscriptionsCount: Long? = 0,
    val isAddedToFavorites : Boolean? = false,
    val isUserSubscribed: Boolean?
)