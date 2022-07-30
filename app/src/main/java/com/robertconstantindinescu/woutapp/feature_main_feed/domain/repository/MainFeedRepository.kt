package com.robertconstantindinescu.woutapp.feature_main_feed.domain.repository

import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.feature_main_feed.domain.model.MainFeedPostDM

interface MainFeedRepository {

    suspend fun getAllPosts(page: Int = 0, pageSize: Int): Resource<List<MainFeedPostDM>>
}