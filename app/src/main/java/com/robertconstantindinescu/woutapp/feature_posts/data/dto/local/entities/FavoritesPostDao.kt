package com.robertconstantindinescu.woutapp.feature_posts.data.dto.local.entities

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesPostDao {

    @Query("SELECT * FROM favorite_post_table ORDER BY savedTimestamp ASC LIMIT :limit OFFSET :offset")
    fun getAllFavoritesPosts(limit: Int = 15, offset: Int): Flow<List<FavoritePostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPostIntoFavorites(favoritePostEntity: FavoritePostEntity)

    @Delete
    suspend fun deletePostFromFavorites(favoritePostEntity: FavoritePostEntity)
}