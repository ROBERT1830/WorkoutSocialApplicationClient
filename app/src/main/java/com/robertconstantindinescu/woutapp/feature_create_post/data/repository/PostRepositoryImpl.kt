package com.robertconstantindinescu.woutapp.feature_create_post.data.repository

import android.content.SharedPreferences
import android.net.Uri
import androidx.core.net.toFile
import com.google.gson.Gson
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.core.util.callApi
import com.robertconstantindinescu.woutapp.feature_create_post.data.remote.PostApi
import com.robertconstantindinescu.woutapp.feature_create_post.data.remote.dto.CreatePostRequest
import com.robertconstantindinescu.woutapp.feature_create_post.domain.repository.PostRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class PostRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val api: PostApi,
    private val gson: Gson
): PostRepository {

    // TODO: get reqeust data from shared?? i think no because we will save all needed data in a state
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

        return when(response.successful) {
            true -> {
                Resource.Success<Unit>()
            }
            false -> {
                response.message?.let {
                    Resource.Error(UiText.DynamicString(response.message))
                } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        }
    }
}