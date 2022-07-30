package com.robertconstantindinescu.woutapp.feature_create_post.domain.use_case

import android.net.Uri
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.SimpleResource
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.feature_create_post.domain.repository.PostRepository

class CreatePostUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(
        sportType: String,
        description: String,
        location: String,
        imageUri: Uri?
    ): DefaultApiResource {

        if (imageUri == null) {
            return Resource.Error(UiText.StringResource(R.string.must_pick_image))
        }

        if (description.trim().isBlank() || location.trim().isBlank()) {
            return Resource.Error(UiText.StringResource(R.string.filed_no_empty))
        }

        return repository.createPost(sportType, description, location, imageUri)
    }
}