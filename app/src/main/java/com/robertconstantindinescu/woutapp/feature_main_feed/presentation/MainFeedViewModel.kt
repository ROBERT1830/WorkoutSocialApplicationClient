package com.robertconstantindinescu.woutapp.feature_main_feed.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.woutapp.core.util.PaginatorImpl
import com.robertconstantindinescu.woutapp.core.util.UiEvent
import com.robertconstantindinescu.woutapp.feature_main_feed.domain.use_case.MainFeedUseCases
import com.robertconstantindinescu.woutapp.feature_main_feed.presentation.mapper.toMainFeedPostVO
import com.robertconstantindinescu.woutapp.feature_main_feed.presentation.model.MainFeedPostVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainFeedViewModel(
    private val useCases: MainFeedUseCases
): ViewModel() {

    var state by mutableStateOf<MainFeedState<MainFeedPostVO>>(MainFeedState())
        private set

    var _uiEvent = Channel<UiEvent<Any>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val paginator = PaginatorImpl(
        onLoad = {
            state = state.copy(
                isLoading = it
            )
        },
        onRequest = { nextPage ->
            useCases.getAllPostsUseCase(nextPage)
        },
        onSuccess = { newPosts ->
            state = state.copy(
                items = state.items + newPosts.map { it.toMainFeedPostVO() },
                isLoading = false,
                endReached = newPosts.isEmpty()
            )
        },
        onError = { text ->
            _uiEvent.send(UiEvent.ShowSnackBar(text))
        }
    )

    init {
        loadNextPosts()
    }

    private fun loadNextPosts() {
        viewModelScope.launch {
            paginator.loadNextPosts()
        }
    }


}