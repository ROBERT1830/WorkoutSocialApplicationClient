package com.robertconstantindinescu.woutapp.feature_create_post.presentation.model

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.components.MainContentPostForms
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.components.MainContentSportType

sealed class CreatePostPage(
    val title: UiText,
    val description: UiText,
    val mainContent: @Composable () -> Unit = {},
) {
    object First: CreatePostPage(
        title = UiText.StringResource(R.string.sport_type),
        description = UiText.StringResource(R.string.pick_sport_type),
        mainContent = {
            MainContentSportType()
        }

    )
    @ExperimentalMaterial3Api
    object Second: CreatePostPage(
        title = UiText.StringResource(R.string.create_post_forms),
        description = UiText.StringResource(R.string.complete_post_forms),
        mainContent = {
            val context = LocalContext.current
            MainContentPostForms(imageLoader = ImageLoader(context = context))
        }
    )
}
