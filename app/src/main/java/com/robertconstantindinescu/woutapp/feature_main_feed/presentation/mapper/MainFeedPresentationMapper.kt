package com.robertconstantindinescu.woutapp.feature_main_feed.presentation.mapper

import com.robertconstantindinescu.woutapp.feature_main_feed.domain.model.MainFeedPostDM
import com.robertconstantindinescu.woutapp.feature_main_feed.presentation.model.MainFeedPostVO

fun MainFeedPostDM.toMainFeedPostVO(): MainFeedPostVO =
    MainFeedPostVO(
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