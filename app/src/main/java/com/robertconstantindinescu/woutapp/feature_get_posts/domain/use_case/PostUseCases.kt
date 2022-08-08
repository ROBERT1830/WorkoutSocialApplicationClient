package com.robertconstantindinescu.woutapp.feature_get_posts.domain.use_case

data class PostUseCases(
    val mainFeedGetAllPostsUseCase: MainFeedGetAllPostsUseCase,
    val personalGetAllPostsUseCase: PersonalGetAllPostsUseCase
)
