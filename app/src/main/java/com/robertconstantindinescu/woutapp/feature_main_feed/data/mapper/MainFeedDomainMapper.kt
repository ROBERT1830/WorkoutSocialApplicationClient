package com.robertconstantindinescu.woutapp.feature_main_feed.data.mapper

import com.robertconstantindinescu.woutapp.feature_main_feed.data.dto.remote.PostRequest
import com.robertconstantindinescu.woutapp.feature_main_feed.domain.model.MainFeedPostDM

fun PostRequest.toMainFeedPostModel(): MainFeedPostDM =
    MainFeedPostDM(
        postId = postId,
        userId = userId,
        userName = userName,
        imageUrl = imageUrl,
        sportType = sportType,
        description = description,
        location = location,
        subscriptionsCount = subscriptionsCount,
        likeCount = likeCount, // I think that wont be used......
        isAddedToFavorites = isAddedToFavorites
    )