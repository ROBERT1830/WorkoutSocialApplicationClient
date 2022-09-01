package com.robertconstantindinescu.woutapp.feature_posts.presentation.common.state

import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.model.PostVO

data class PostToDeleteState(
    val post: PostVO? =  null
)