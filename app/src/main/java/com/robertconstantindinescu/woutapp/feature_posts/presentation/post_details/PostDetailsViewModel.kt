package com.robertconstantindinescu.woutapp.feature_posts.presentation.post_details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.UiEvent
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.feature_posts.domain.use_case.PostUseCases
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.mapper.toPostVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val useCases: PostUseCases,
    private val savedStateHandle: SavedStateHandle
): ViewModel()  {

    var state by mutableStateOf(PostDetailsState())
        private set

    private var _uiEvent = Channel<UiEvent<Any>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        savedStateHandle.get<String>("postId")?.let { postId ->
            loadPostDetails(postId)
            Log.d("postIdVM", postId)

        }
    }

    private fun loadPostDetails(postId: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            useCases.getPostDetailsUseCase(postId).mapResourceData(
                success = { post ->
                    state = state.copy(
                        isLoading = false,
                        post = post?.toPostVO()
                    )
                },
                error = { text, _ ->
                    state = state.copy(
                        isLoading = false,
                    )
                    _uiEvent.send(UiEvent.ShowSnackBar(text ?: UiText.unknownError()))
                }
            )
        }
    }
}