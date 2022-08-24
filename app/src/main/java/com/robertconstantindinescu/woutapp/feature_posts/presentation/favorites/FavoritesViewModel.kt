package com.robertconstantindinescu.woutapp.feature_posts.presentation.favorites

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.UiEvent
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.core.util.paginator.Paginator
import com.robertconstantindinescu.woutapp.core.util.paginator.PaginatorImpl
import com.robertconstantindinescu.woutapp.core.util.paginator.PaginatorImpl2
import com.robertconstantindinescu.woutapp.core.util.subscriptor.PostSubscriptor
import com.robertconstantindinescu.woutapp.feature_posts.domain.model.PostDM
import com.robertconstantindinescu.woutapp.feature_posts.domain.use_case.PostUseCases
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.mapper.toPostDM
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.mapper.toPostVO
import com.robertconstantindinescu.woutapp.feature_posts.presentation.main_feed_screen.MainFeedEvent
import com.robertconstantindinescu.woutapp.feature_posts.presentation.main_feed_screen.model.PostVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val useCases: PostUseCases,
    private val postSubscriptor: PostSubscriptor
    ) : ViewModel() {

    var state by mutableStateOf(FavoritesState())
        private set

    private var _uiEvent = Channel<UiEvent<Any>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getFavoriteMovies: Job? = null
    private lateinit var oldList: List<PostVO>


    private val paginator = PaginatorImpl2<Int, PostDM>(
        initialKey = state.page,
        onLoad = {
            state = state.copy(
                isLoading = it
            )
        },
        getNextKey = {
            state.page + 1
        },
        onRequest = { nextPage ->
            useCases.getAllFavoritesPostsUseCase(nextPage)

        },
        onSuccess = { newPosts, nextPage ->
            state = state.copy(
                items = state.items + newPosts.map { it.toPostVO() },
                isLoading = false,
                endReached = newPosts.isEmpty(),
                page = nextPage
            )
        }
    )

    init {
        loadFavoritesPosts()
    }

    fun onEvent(event: FavoritesEvent) {
        when(event) {
            is FavoritesEvent.onDeleteFavoritePost -> {
                deleteFavoritePost(event.post)
            }
            is FavoritesEvent.OnToggleSubscription -> {
                toogleSubscription(event.postId)
            }
        }
    }

    private fun deleteFavoritePost(post: PostVO) {
        viewModelScope.launch {

            val oldList = state.items
            state = state.copy(isLoading = true)

            useCases.deleteFromFavoritesUseCase(post.toPostDM())
            // TODO: review the deletion
//            state = state.copy(
//                isLoading = false,
//                items = if (state.items.toMutableList().remove(post)) {
//                    state.items
//                } else {
//                    emptyList()
//                }
//            )
            state = FavoritesState()
            reloadFavoritesPosts()
            Log.d("items", state.items.toString())
            _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.favorites_screen_post_deleted)))
        }
    }

    private fun reloadFavoritesPosts() {
        viewModelScope.launch {
            paginator.reset()
            paginator.loadNextPosts()
        }
    }

    fun loadFavoritesPosts() {
        //getFavoriteMovies?.cancel()
        Log.d("items", state.items.toString())
        viewModelScope.launch {
            paginator.loadNextPosts()
        }
    }

    private fun toogleSubscription(postId: String) {
        viewModelScope.launch {
            postSubscriptor.togglePostSubscriptor(
                posts = state.items,
                postId = postId,
                onRequest = { isUserSubscribed ->
                    useCases.toggleSubscribtionUseCase(postId, isUserSubscribed)
                },
                onStateUpdate = { posts ->
                    state = state.copy(
                        items = posts
                    )
                }
            )
        }
    }
}