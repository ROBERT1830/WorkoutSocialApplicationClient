package com.robertconstantindinescu.woutapp.feature_posts.data.dto.local.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.robertconstantindinescu.woutapp.feature_posts.data.util.Constants.FAVORITE_POST_TABLE

@Entity(tableName = FAVORITE_POST_TABLE)
data class FavoritePostEntity(
    @PrimaryKey(autoGenerate = false)
    val postId: String,
    val userId: String?,
    val userName: String?,
    val imageUrl: String?,
    val profileImage: String?,
    val sportType: String?,
    val description: String?,
    val location: String?,
    val subscriptionsCount: Long? = 0,
    val isAddedToFavorites : Boolean?,
    val isUserSubscribed: Boolean?,
    val savedTimestamp: Long?
)
