package com.robertconstantindinescu.woutapp.feature_posts.domain.use_case

data class PostUseCases(
    val mainFeedGetAllPostsUseCase: MainFeedGetAllPostsUseCase,
    val personalGetAllPostsUseCase: PersonalGetAllPostsUseCase,
    val toggleFavoritesUseCase: InsertPostIntoFavoritesUseCase,
    val deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase,
    val getAllFavoritesPostsUseCase: GetAllFavoritesPostsUseCase,
    val toggleSubscribtionUseCase: ToggleSubscribtionUseCase,
    val getPostDetailsUseCase: GetPostDetailsUseCase,
    val deletePostFromRemoteUseCase: DeletePostFromRemoteUseCase
)
