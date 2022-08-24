package com.robertconstantindinescu.woutapp.feature_create_post.data.repository

import android.net.Uri
import androidx.core.net.toFile
import com.google.gson.Gson
import com.robertconstantindinescu.woutapp.core.util.*
import com.robertconstantindinescu.woutapp.feature_create_post.data.remote.PostApi
import com.robertconstantindinescu.woutapp.feature_create_post.data.remote.dto.CreatePostRequest
import com.robertconstantindinescu.woutapp.feature_create_post.domain.repository.CreatePostRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class CreatePostRepositoryImpl(
    private val api: PostApi,
    private val gson: Gson
): CreatePostRepository {

    override suspend fun createPost(sportType: String, description: String, location: String, imageUri: Uri): DefaultApiResource {
        val imageFile = imageUri.toFile()

        val response = callApi {
            api.createPost(
                postData = MultipartBody.Part.createFormData(
                    "post_data",
                    value = gson.toJson(CreatePostRequest(
                        sportType = sportType,
                        description = description,
                        location = location
                    ))
                ),
                postImage = MultipartBody.Part.createFormData(
                    name = "post_image",
                    filename = imageFile.name,
                    body = imageFile.asRequestBody()
                )
            )
        }

        return response.simpleApiResponseCheck()
    }
}