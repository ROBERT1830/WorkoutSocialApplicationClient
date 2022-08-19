package com.robertconstantindinescu.woutapp.feature_posts.data.mapper

import com.robertconstantindinescu.woutapp.feature_posts.data.dto.local.entities.FavoritePostEntity
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.remote.response.PostDto
import com.robertconstantindinescu.woutapp.feature_posts.domain.model.PostDM

fun PostDto.toPostDM(): PostDM =
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

fun FavoritePostEntity.toPostDM(): PostDM =
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

fun PostDM.toFavoritePostEntity(): FavoritePostEntity =
    FavoritePostEntity(
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
        isUserSubscribed = isUserSubscribed,
        savedTimestamp = System.currentTimeMillis()
    )

