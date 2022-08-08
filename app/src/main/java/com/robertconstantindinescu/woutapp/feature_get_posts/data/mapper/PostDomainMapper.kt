package com.robertconstantindinescu.woutapp.feature_get_posts.data.mapper

import com.robertconstantindinescu.woutapp.feature_get_posts.data.dto.remote.PostDto
import com.robertconstantindinescu.woutapp.feature_get_posts.domain.model.PostDM

fun PostDto.toMainFeedPostDM(): PostDM =
    PostDM(
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

