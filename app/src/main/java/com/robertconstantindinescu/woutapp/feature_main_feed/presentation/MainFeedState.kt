package com.robertconstantindinescu.woutapp.feature_main_feed.presentation

data class MainFeedState<T>(
    val items: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false
)
