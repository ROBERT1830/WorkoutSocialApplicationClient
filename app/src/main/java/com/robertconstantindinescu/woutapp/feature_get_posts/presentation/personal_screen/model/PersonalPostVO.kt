package com.robertconstantindinescu.woutapp.feature_get_posts.presentation.personal_screen.model

data class PersonalPostVO(
    val postId: String?,
    val userId: String?,
    val userName: String?,
    val imageUrl: String?,
    val profileImage: String?,
    val sportType: String?,
    val description: String?,
    val location: String?,
    val subscriptionsCount: Long? = 0
)