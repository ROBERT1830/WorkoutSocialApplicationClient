package com.robertconstantindinescu.woutapp.feature_posts.presentation.main_feed_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.LocalSpacing
import com.robertconstantindinescu.woutapp.core.util.extensions.sharePost
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.components.Post

@ExperimentalMaterial3Api
@Composable
fun MainFeedScreen(
    imageLoader: ImageLoader,
    viewModel: MainFeedScreenViewModel = hiltViewModel(),
    onDetailNavigate: (postId: String) -> Unit = {}
) {

    val context = LocalContext.current
    val dimens = LocalSpacing.current
    val state = viewModel.mainFeedScreenState

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn {
                items(state.items.size) { index ->
                    //get post at scroll position
                    val post = state.items[index]
                    if ((index > state.items.size - 1 && state.items.isNotEmpty()) && !state.endReached && !state.isLoading) {
                        viewModel.loadNextPosts()
                    }
                    Post(
                        post = post,
                        imageLoader = imageLoader,
                        shareEnabled = true,
                        favoritesEnabled = true,
                        subscribeEnabled = true,
                        onFavoritesClick = {
                            viewModel.onEvent(MainFeedEvent.OnFavoriteClick(post))
                        },
                        onSubscribeClick = {
                            viewModel.onEvent(MainFeedEvent.OnToggleSubscription(post.postId))
                        },
                        onDetailNavigate = {
                            onDetailNavigate(it)
                        },
                        onShareClick = { postId ->
                            context.sharePost(postId)
                        }
                    )
                    Log.d("TAGcurrentIndex", (state.items.size - 1).toString())
                    Log.d("TAGendReached", state.endReached.toString())
                    Log.d("TAGsize", (state.items.size).toString())
                    if (index == state.items.size - 1) {
                        Log.d("paddingTop", "called")
                        Spacer(modifier = Modifier.height(dimens.spaceExtraLarge + dimens.spaceSmall))
                    }
                }
            }
        }
        if (state.isShowNoPostText) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.no_data_available),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.primary)
        }
    }


}