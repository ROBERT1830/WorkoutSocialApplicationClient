package com.robertconstantindinescu.woutapp.feature_main_feed.data.mapper

import com.robertconstantindinescu.woutapp.feature_main_feed.data.dto.remote.PostDto
import com.robertconstantindinescu.woutapp.feature_main_feed.domain.model.MainFeedPostDM

fun PostDto.toMainFeedPostModel(): MainFeedPostDM =
    MainFeedPostDM(
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