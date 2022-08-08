package com.robertconstantindinescu.woutapp.feature_get_posts.presentation.personal_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import coil.ImageLoader
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.LocalSpacing
import com.robertconstantindinescu.woutapp.core.util.LifecycleEventListener
import com.robertconstantindinescu.woutapp.feature_get_posts.presentation.common.components.Post

@ExperimentalMaterial3Api
@Composable
fun PersonalScreen(
    imageLoader: ImageLoader,
    onDetailNavigate: (postId: String) -> Unit = {},
    viewModel: PersonalScreenViewModel = hiltViewModel()
) {

    val dimens = LocalSpacing.current
    val state = viewModel.personalScreenState

    LifecycleEventListener { lifeCycle ->
        when(lifeCycle) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.initialLoadPosts()
            }
            else -> {}
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        LazyColumn {
            items(state.items.size) { index ->
                //get post at scroll position
                val post = state.items[index]
                if (index >= state.items.size - 1 && !state.endReached && !state.isLoading) {
                    viewModel.loadNextPosts()
                }
                Post(post = post, imageLoader = imageLoader)
                if (index == state.items.size - 1 && state.endReached) {
                    Spacer(modifier = Modifier.height(dimens.spaceExtraLarge + dimens.spaceSmall))
                }
            }
        }
    }

}