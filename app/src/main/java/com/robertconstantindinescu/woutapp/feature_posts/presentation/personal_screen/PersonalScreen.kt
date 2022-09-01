package com.robertconstantindinescu.woutapp.feature_posts.presentation.personal_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.robertconstantindinescu.woutapp.core.presentation.components.ConfirmationDialog
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.LocalSpacing
import com.robertconstantindinescu.woutapp.core.util.LifecycleEventListener
import com.robertconstantindinescu.woutapp.core.util.UiEvent
import com.robertconstantindinescu.woutapp.core.util.extensions.sharePost
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.components.Post
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterial3Api
@Composable
fun PersonalScreen(
    imageLoader: ImageLoader,
    scaffoldState: ScaffoldState,
    onDetailNavigate: (postId: String) -> Unit = {},
    viewModel: PersonalScreenViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val dimens = LocalSpacing.current
    val state = viewModel.personalScreenState
    val postToDeleteState = viewModel.postToDelete

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = uiEvent.text.asString(context)
                    )
                }
                else -> Unit
            }
        }
    }

    LifecycleEventListener { lifeCycle ->
        when (lifeCycle) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.initialLoadPosts()
            }
            else -> {}
        }
    }

    Box(
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
                    deleteEnabled = true,
                    onShareClick = { postId ->
                        context.sharePost(postId)
                    },
                    onDeletePost = { post ->
                        viewModel.deletePostToState(post)
                        showDialog = true
                    }
                )
                if (index == state.items.size - 1) {
                    Spacer(modifier = Modifier.height(dimens.spaceExtraLarge + dimens.spaceSmall))
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
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
        if (showDialog) {
            ConfirmationDialog(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(id = R.string.dialog_title),
                description = stringResource(id = R.string.dialog_description),
                onDismiss = { showDialog = false },
                onConfirm = {
                    //never will be null because at deletion we set the variable
                    viewModel.deletePostFromRemote(postToDeleteState.post!!)
                    showDialog = false
                }
            )
        }

    }

}