package com.robertconstantindinescu.woutapp.feature_get_posts.presentation.personal_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.woutapp.core.util.PaginatorImpl
import com.robertconstantindinescu.woutapp.core.util.UiEvent
import com.robertconstantindinescu.woutapp.feature_get_posts.domain.use_case.PostUseCases
import com.robertconstantindinescu.woutapp.feature_get_posts.presentation.common.state.PostState
import com.robertconstantindinescu.woutapp.feature_get_posts.presentation.common.mapper.toPostVO
import com.robertconstantindinescu.woutapp.feature_get_posts.presentation.main_feed_screen.model.PostVO
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

}