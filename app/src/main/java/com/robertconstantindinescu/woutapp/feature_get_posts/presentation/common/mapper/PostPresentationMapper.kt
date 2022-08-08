package com.robertconstantindinescu.woutapp.feature_get_posts.presentation.common.mapper

import com.robertconstantindinescu.woutapp.feature_get_posts.domain.model.PostDM
import com.robertconstantindinescu.woutapp.feature_get_posts.presentation.main_feed_screen.model.PostVO

fun PostDM.toPostVO(): PostVO =
    PostVO(
        postId = postId,
        userId = userId,
        userName = userName,
        imageUrl = imageUrl,
        profileImage = profileImage,
        sportType = sportType,
        description = description,
        location = location,
        subscriptionsCount = subscriptionsCount,
        isAddedToFavorites = isAddedToFavorites,
        isUserSubscribed = isUserSubscribed
    )
