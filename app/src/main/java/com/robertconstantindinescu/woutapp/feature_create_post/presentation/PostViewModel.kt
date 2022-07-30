package com.robertconstantindinescu.woutapp.feature_create_post.presentation

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.UiEvent
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.feature_create_post.domain.use_case.PostUseCases
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.state.FormsState
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.state.SportTypeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val useCases: PostUseCases
) : ViewModel() {

    var sportTypeState by mutableStateOf(SportTypeState())
        private set

    var formsState by mutableStateOf(FormsState())
        private set

    var postImageState by mutableStateOf<Uri?>(null)
        private set

    var isLoading by mutableStateOf<Boolean>(false)
        private set

    private var _uiEvent = Channel<UiEvent<Any>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: PostEvents) {
        when (event) {
            is PostEvents.onSelectSportType -> {
                sportTypeState = SportTypeState()
                sportTypeState = sportTypeState.copy(
                    sports = sportTypeState.sports.map {
                        if (it.type == event.sport.type) {
                            it.copy(
                                isSelected = !it.isSelected
                            )
                        } else {
                            it
                        }
                    }
                )
            }
            is PostEvents.onEnterDesscription -> {
                formsState = formsState.copy(
                    description = event.description
                )

            }
            is PostEvents.onEnterLocation -> {
                formsState = formsState.copy(
                    location = event.location
                )
            }
            is PostEvents.onPickPhoto -> {
                postImageState = event.uri
            }
            is PostEvents.onCropImage -> {
                postImageState = event.uri
            }
            is PostEvents.onCreatePostClick -> {
                createPost(event.context)
            }
        }
    }

    private fun createPost(context: Context) {
        viewModelScope.launch {
            isLoading = true

            val result = sportTypeState.sports.firstOrNull {
                it.isSelected
            }?.type?.let {
                useCases.createPostUseCase(
                    sportType = it.asString(context),
                    description = formsState.description,
                    location = formsState.location,
                    imageUri = postImageState
                )
            } ?:  kotlin.run {
                _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.must_select_sport)))
                return@launch
            }

            when(result) {
                is Resource.Success -> {
                    postImageState = null
                    formsState = FormsState()
                    sportTypeState = SportTypeState()
                    isLoading = false
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.post_created)))
                    _uiEvent.send(UiEvent.NavigateUp(null))

                }
                is Resource.Error -> {
                    isLoading = false
                    _uiEvent.send(UiEvent.ShowSnackBar(result.text ?: UiText.unknownError()))
                }
            }
        }
    }


}