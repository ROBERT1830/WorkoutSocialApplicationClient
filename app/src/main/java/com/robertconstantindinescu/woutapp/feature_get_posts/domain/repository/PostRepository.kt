package com.robertconstantindinescu.woutapp.feature_get_posts.domain.repository

import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.feature_get_posts.domain.model.PostDM

interface PostRepository {

    suspend fun getAllPosts(page: Int = 0, pageSize: Int): Resource<List<PostDM>>
    suspend fun getAllCurrentUserPosts(page: Int = 0, pageSize: Int): Resource<List<PostDM>>
}