package com.robertconstantindinescu.woutapp.feature_create_post.presentation

import android.content.Context
import android.net.Uri
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.model.Sport

sealed class PostEvents {
    data class onSelectSportType(val sport: Sport): PostEvents()
    data class onEnterDesscription(val description: String): PostEvents()
    data class onEnterLocation(val location: String): PostEvents()
    data class onPickPhoto(val uri: Uri?): PostEvents()
    data class onCropImage(val uri: Uri?): PostEvents()
    data class onCreatePostClick(val context: Context): PostEvents()
}
