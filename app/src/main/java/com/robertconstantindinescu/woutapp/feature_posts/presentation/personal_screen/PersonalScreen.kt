package com.robertconstantindinescu.woutapp.feature_posts.presentation.personal_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import coil.ImageLoader
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.LocalSpacing
import com.robertconstantindinescu.woutapp.core.util.LifecycleEventListener
import com.robertconstantindinescu.woutapp.core.util.extensions.sharePost
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.components.Post

@ExperimentalMaterial3Api
@Composable
fun PersonalScreen(
    imageLoader: ImageLoader,
    onDetailNavigate: (postId: String) -> Unit = {},
    viewModel: PersonalScreenViewModel = hiltViewModel()
) {

    val context = LocalContext.current
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

    Box {
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
                    Post(
                        post = post,
                        imageLoader = imageLoader,
                        shareEnabled = true,
                        deleteEnabled = true,
                        onShareClick = { postId ->
                            context.sharePost(postId)
                        },
                        onDeletePost = {

                        }
                    )
                    if (index == state.items.size - 1 && state.endReached) {
                        Spacer(modifier = Modifier.height(dimens.spaceExtraLarge + dimens.spaceSmall))
                    }
                }
            }
        }
        if (state.items.isEmpty()) {
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