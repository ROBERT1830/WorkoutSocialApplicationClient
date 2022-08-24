package com.robertconstantindinescu.woutapp.feature_posts.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.LocalSpacing
import com.robertconstantindinescu.woutapp.core.util.UiEvent
import com.robertconstantindinescu.woutapp.core.util.extensions.sharePost
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.components.Post
import com.robertconstantindinescu.woutapp.feature_posts.presentation.main_feed_screen.MainFeedEvent
import com.robertconstantindinescu.woutapp.feature_posts.presentation.main_feed_screen.MainFeedScreenViewModel
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterial3Api
@Composable
fun FavoriteScreen(
    imageLoader: ImageLoader,
    scaffoldState: ScaffoldState,
    viewModel: FavoritesViewModel = hiltViewModel(),
    onDetailNavigate: (postId: String) -> Unit = {}
) {
    val context = LocalContext.current
    val dimens = LocalSpacing.current
    val state = viewModel.state

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { uiEvent ->
            when(uiEvent) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = uiEvent.text.asString(context)
                    )
                }
                else -> Unit
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        LazyColumn {
            items(state.items.size) { index ->
                val post = state.items[index]
                if (index >= state.items.size - 1 && !state.endReached && !state.isLoading) {
                    viewModel.loadFavoritesPosts()
                }
                Post(
                    post = post,
                    imageLoader = imageLoader,
                    deleteEnabled = true,
                    onSubscribeClick = {
                        viewModel.onEvent(FavoritesEvent.OnToggleSubscription(post.postId))
                    },
                    onDetailNavigate = {
                        onDetailNavigate(it)
                    },
                    onShareClick = { postId ->
                        context.sharePost(postId)
                    },
                    onDeleteFavoritePost = { post ->
                        viewModel.onEvent(FavoritesEvent.onDeleteFavoritePost(post))
                    }
                )
                if (index == state.items.size - 1 && state.endReached) {
                    Spacer(modifier = Modifier.height(dimens.spaceExtraLarge + dimens.spaceSmall))
                }
            }
        }
    }
}