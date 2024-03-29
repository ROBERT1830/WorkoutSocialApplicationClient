package com.robertconstantindinescu.woutapp.feature_posts.presentation.main_feed_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.woutapp.core.util.paginator.PaginatorImpl
import com.robertconstantindinescu.woutapp.core.util.UiEvent
import com.robertconstantindinescu.woutapp.core.util.subscriptor.PostSubscriptor
import com.robertconstantindinescu.woutapp.feature_posts.domain.use_case.PostUseCases
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.mapper.toPostDM
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.state.PostState
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.mapper.toPostVO
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.model.PostVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFeedScreenViewModel @Inject constructor(
    private val useCases: PostUseCases,
    private val postSubscriptor: PostSubscriptor
) : ViewModel() {

    var mainFeedScreenState by mutableStateOf<PostState<PostVO>>(PostState())
        private set

    private var _uiEvent = Channel<UiEvent<Any>>()
    val uiEvent = _uiEvent.receiveAsFlow()


    private val paginator = PaginatorImpl(
        initialKey = mainFeedScreenState.page,
        onLoad = {
            mainFeedScreenState = mainFeedScreenState.copy(
                isLoading = it
            )
        },
        onRequest = { nextPage ->
            useCases.mainFeedGetAllPostsUseCase(nextPage)
        },
        getNextKey = {
            mainFeedScreenState.page + 1
        },
        onSuccess = { newPosts, nextPage ->

            if ((mainFeedScreenState.items + newPosts).isEmpty()) {
                mainFeedScreenState = mainFeedScreenState.copy(
                    isLoading = false,
                    isShowNoPostText = true,
                    items = emptyList(),
                    endReached = newPosts.isEmpty()
                )
                return@PaginatorImpl
            }
            mainFeedScreenState = mainFeedScreenState.copy(
                items = mainFeedScreenState.items + newPosts.map { it.toPostVO() },
                isLoading = false,
                endReached = newPosts.isEmpty(),
                page = nextPage
            )


        },
        onError = { text ->
            _uiEvent.send(UiEvent.ShowSnackBar(text))
        }
    )

    init {
        loadNextPosts()
    }

    fun onEvent(event: MainFeedEvent) {
        when (event) {
            is MainFeedEvent.OnFavoriteClick -> {
                savePostIntoFavorites(event.post)
            }
            is MainFeedEvent.OnToggleSubscription -> {
                toogleSubscription(event.postId)
            }
        }
    }


    private fun savePostIntoFavorites(post: PostVO) {
        viewModelScope.launch {

            postSubscriptor.togglePostFavorites(
                posts = mainFeedScreenState.items,
                postId = post.postId,
                onRequest = { isPostliked ->
                    useCases.toggleFavoritesUseCase(post.toPostDM(), isPostliked)
                },
                onStateUpdate = { posts ->
                    mainFeedScreenState = mainFeedScreenState.copy(
                        items = posts
                    )
                }
            )
        }
    }

    fun loadNextPosts() {
        viewModelScope.launch {
            paginator.loadNextPosts()
        }
    }

    private fun toogleSubscription(postId: String) {
        viewModelScope.launch {
            postSubscriptor.togglePostSubscripton(
                posts = mainFeedScreenState.items,
                postId = postId,
                onRequest = { isUserSubscribed ->
                    useCases.toggleSubscribtionUseCase(postId, isUserSubscribed)
                    // TODO: create the succes
                },
                onStateUpdate = { posts ->
                    mainFeedScreenState = mainFeedScreenState.copy(
                        items = posts
                    )
                }
            )
        }
    }


}