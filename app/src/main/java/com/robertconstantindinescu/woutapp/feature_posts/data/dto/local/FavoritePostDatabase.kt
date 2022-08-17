package com.robertconstantindinescu.woutapp.feature_posts.data.dto.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.local.entities.FavoritePostEntity
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.local.entities.FavoritesPostDao

@Database(entities = [FavoritePostEntity::class], version = 1)
abstract class FavoritePostDatabase: RoomDatabase() {
    abstract val dao: FavoritesPostDao
}