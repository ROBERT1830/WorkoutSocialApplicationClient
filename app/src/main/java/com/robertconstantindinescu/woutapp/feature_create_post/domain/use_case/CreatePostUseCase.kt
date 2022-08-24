package com.robertconstantindinescu.woutapp.feature_create_post.domain.use_case

import android.net.Uri
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.feature_create_post.domain.repository.CreatePostRepository

class CreatePostUseCase(
    private val repositoryCreate: CreatePostRepository
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

        if (description.isBlank() || location.isBlank()) {
            return Resource.Error(UiText.StringResource(R.string.filed_no_empty))
        }

        return repositoryCreate.createPost(sportType, description, location, imageUri)
    }
}