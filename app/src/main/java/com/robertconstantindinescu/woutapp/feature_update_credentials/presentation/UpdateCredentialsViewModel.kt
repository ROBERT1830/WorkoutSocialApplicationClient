package com.robertconstantindinescu.woutapp.feature_update_credentials.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.UiEvent
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.feature_authentication.presentation.util.AuthStandardFieldState
import com.robertconstantindinescu.woutapp.feature_update_credentials.domain.use_case.UpdateCredentialsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateCredentialsViewModel @Inject constructor(
    private val useCases: UpdateCredentialsUseCases
    ) : ViewModel() {


    var usernameState by mutableStateOf(AuthStandardFieldState())
        private set

    var emailState by mutableStateOf(AuthStandardFieldState())
        private set

    var updateCredentialState by mutableStateOf(UpdateCredentialsState())
        private set

    private var _uiEvent = Channel<UiEvent<Any>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadUserCredentials()
    }

    fun onEvent(event: UpdateCredentialsEvent) {
        when(event) {
            is UpdateCredentialsEvent.OnEnterUserName -> {
                usernameState = usernameState.copy(
                    text = event.name
                )
            }
            is UpdateCredentialsEvent.OnEnterEmail -> {
                emailState = emailState.copy(
                    text = event.email
                )
            }
            is UpdateCredentialsEvent.OnUpdateCredentials -> {
                updateUserCredentials()
            }
        }

    }

    private fun updateUserCredentials() {
        viewModelScope.launch {
            updateCredentialState = updateCredentialState.copy(isLoading = true)
            usernameState = usernameState.copy(error = null)
            emailState = emailState.copy(error = null)

            val updateUserResult = useCases.updateCredentialsUseCases(
                usernameState.text,
                emailState.text
            )
            if (updateUserResult.usernameError != null) {
                usernameState = usernameState.copy(error = updateUserResult.usernameError)
            }

            if (updateUserResult.emailError != null) {
                emailState = emailState.copy(error = updateUserResult.emailError)
            }

            when(updateUserResult.resultError) {
                is Resource.Success -> {
                    updateCredentialState = updateCredentialState.copy(isLoading = false)
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.user_credentials_updated)))
                }
                is Resource.Error -> {
                    updateCredentialState = updateCredentialState.copy(isLoading = false)
                    _uiEvent.send(UiEvent.ShowSnackBar(updateUserResult.resultError.text ?: UiText.unknownError()))
                }
                null ->updateCredentialState = updateCredentialState.copy(isLoading = false)
            }
        }
    }

    private fun loadUserCredentials() {
        viewModelScope.launch {
            when (val response = useCases.getUserCredentialsUseCase()) {
                is Resource.Success -> {
                    usernameState = usernameState.copy(
                        text = response.data?.name ?: ""
                    )
                    emailState = emailState.copy(
                        text = response.data?.email ?: ""
                    )
                }
                is Resource.Error -> {
                    _uiEvent.send(UiEvent.ShowSnackBar(response.text ?: UiText.unknownError()))
                    return@launch
                }
            }
        }
    }
}