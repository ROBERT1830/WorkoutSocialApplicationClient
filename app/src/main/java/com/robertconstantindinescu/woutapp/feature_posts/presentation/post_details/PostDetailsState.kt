package com.robertconstantindinescu.woutapp.feature_posts.presentation.post_details

import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.model.PostVO

data class PostDetailsState(
    val post: PostVO? = null,
    val isLoading: Boolean = false
)
