package com.robertconstantindinescu.woutapp.feature_create_post.domain.repository

import android.net.Uri
import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource

interface PostRepository {
    suspend fun createPost(sportType: String, description: String, location: String, imageUri: Uri): DefaultApiResource
}