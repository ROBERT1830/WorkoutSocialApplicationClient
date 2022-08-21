package com.robertconstantindinescu.woutapp.feature_posts.presentation.favorites

import com.robertconstantindinescu.woutapp.feature_posts.presentation.main_feed_screen.model.PostVO

data class FavoritesState(
    val items: List<PostVO> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val error: String? = null,
    val page: Int = 0
)
