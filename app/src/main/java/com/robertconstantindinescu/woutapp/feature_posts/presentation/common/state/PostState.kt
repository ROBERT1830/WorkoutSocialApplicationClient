package com.robertconstantindinescu.woutapp.feature_posts.presentation.common.state



data class PostState<T>(
    val items: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val error: String? = null,
    val page: Int = 0
)
