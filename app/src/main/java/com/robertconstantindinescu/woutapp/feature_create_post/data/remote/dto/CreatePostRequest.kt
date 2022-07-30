package com.robertconstantindinescu.woutapp.feature_create_post.data.remote.dto

data class CreatePostRequest(
    val sportType: String,
    val description: String,
    val location: String,
)
