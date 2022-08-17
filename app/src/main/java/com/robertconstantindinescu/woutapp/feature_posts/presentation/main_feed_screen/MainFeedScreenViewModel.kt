package com.robertconstantindinescu.woutapp.feature_posts.presentation.main_feed_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.woutapp.core.util.PaginatorImpl
import com.robertconstantindinescu.woutapp.core.util.UiEvent
import com.robertconstantindinescu.woutapp.feature_posts.domain.use_case.PostUseCases
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.mapper.toPostDM
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.state.PostState
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.mapper.toPostVO
import com.robertconstantindinescu.woutapp.feature_posts.presentation.main_feed_screen.model.PostVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFeedScreenViewModel @Inject constructor(
    private val useCases: PostUseCases
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
            //check with less amount thaqn 10 per load and see if the pagination works fine
//            personalScreenState = when (reset) {
//
////                true -> {
////                    //reload post from initial
////                    personalScreenState.copy(
////                        items = newPosts.map { it.toMainFeedPostVO() },
////                        isLoading = false,
////                        endReached = newPosts.isEmpty()
////                    )
////                }
//
//                //false -> {
//                    personalScreenState.copy(
//                        items = personalScreenState.items + newPosts.map { it.toMainFeedPostVO() },
//                        isLoading = false,
//                        endReached = newPosts.isEmpty()
//                    )
//                //}
//            }
            mainFeedScreenState = mainFeedScreenState.copy(
                items = mainFeedScreenState.items + newPosts.map { it.toPostVO() },
                isLoading = false,
                endReached = newPosts.isEmpty(),
                page = nextPage
            )
            //}

        },
        onError = { text ->
            _uiEvent.send(UiEvent.ShowSnackBar(text))
        }
    )

    init {
        loadNextPosts()
    }

    fun onEvent(event: MainFeedEvent) {
        when(event) {
            is MainFeedEvent.OnFavoriteClick -> {
                savePostIntoFavorites(event.post)
            }
        }
    }

    private fun savePostIntoFavorites(post: PostVO) {
        viewModelScope.launch {
            useCases.insertPostIntoFavoritesUseCase(post.toPostDM())
        }
    }

    fun loadNextPosts() {
        viewModelScope.launch {
            paginator.loadNextPosts()
        }
    }



}