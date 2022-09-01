package com.robertconstantindinescu.woutapp.feature_posts.presentation.personal_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.util.paginator.PaginatorImpl
import com.robertconstantindinescu.woutapp.core.util.UiEvent
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.feature_posts.domain.use_case.PostUseCases
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.state.PostState
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.mapper.toPostVO
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.state.PostToDeleteState
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.model.PostVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalScreenViewModel @Inject constructor(
    private val useCases: PostUseCases
) : ViewModel() {

    var personalScreenState by mutableStateOf<PostState<PostVO>>(PostState())
        private set

    var postToDelete by mutableStateOf(PostToDeleteState())
        private set

    private var _uiEvent = Channel<UiEvent<Any>>()
    val uiEvent = _uiEvent.receiveAsFlow()


    private val paginator = PaginatorImpl(
        initialKey = personalScreenState.page,
        onLoad = {
            personalScreenState = personalScreenState.copy(
                isLoading = it
            )
        },
        onRequest = { nextPage ->
            useCases.personalGetAllPostsUseCase(nextPage)
        },
        getNextKey = {
            personalScreenState.page + 1
        },
        onSuccess = { newPosts, nextPage ->
            personalScreenState = personalScreenState.copy(
                items = personalScreenState.items + newPosts.map { it.toPostVO() },
                isLoading = false,
                endReached = newPosts.isEmpty(),
                page = nextPage
            )
            if ((personalScreenState.items + newPosts).isEmpty()) {
                personalScreenState = personalScreenState.copy(
                    isLoading = false,
                    isShowNoPostText = true
                )
            }
        },
        onError = { text ->
            _uiEvent.send(UiEvent.ShowSnackBar(text))
        }
    )

    fun loadNextPosts() {
        viewModelScope.launch {
            paginator.loadNextPosts()
        }
    }


    fun initialLoadPosts() {
        viewModelScope.launch {
            personalScreenState = PostState()
            paginator.reset()
            paginator.loadNextPosts()
        }
    }

    fun deletePostFromRemote(post: PostVO) {
        viewModelScope.launch {
            personalScreenState = personalScreenState.copy(isLoading = true)
            useCases.deletePostFromRemoteUseCase(post.postId).mapResourceData(
                success = {
                    val newPostList = personalScreenState.items.toMutableList()
                    newPostList.remove(post)
                    if (newPostList.isEmpty()) {
                        personalScreenState = personalScreenState.copy(
                            isLoading = false,
                            isShowNoPostText = true,
                            items = emptyList()
                        )
                        return@launch
                    }
                    personalScreenState = personalScreenState.copy(
                        isLoading = false,
                        items = newPostList.toList(),
                        isShowNoPostText = false
                    )
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.favorites_screen_post_deleted)))
                },
                error = { text, _ ->
                    personalScreenState = personalScreenState.copy(isLoading = false, isShowNoPostText = true)
                    _uiEvent.send(UiEvent.ShowSnackBar(text ?: UiText.unknownError()))
                }
            )

        }
    }

    fun deletePostToState(post: PostVO) {
        postToDelete = postToDelete.copy(post = post)
    }
}