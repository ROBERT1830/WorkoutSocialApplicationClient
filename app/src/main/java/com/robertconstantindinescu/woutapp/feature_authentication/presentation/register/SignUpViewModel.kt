package com.robertconstantindinescu.woutapp.feature_authentication.presentation.register

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
import com.robertconstantindinescu.woutapp.feature_authentication.domain.use_case.AuthUseCases
import com.robertconstantindinescu.woutapp.feature_authentication.presentation.util.PasswordState
import com.robertconstantindinescu.woutapp.feature_authentication.presentation.util.DefaultFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userCases: AuthUseCases
) : ViewModel() {

    var profileImageState by mutableStateOf<Uri?>(null)
        private set

    var usernameState by mutableStateOf(DefaultFieldState())
        private set

    var emailState by mutableStateOf(DefaultFieldState())
        private set

    var passwordState by mutableStateOf(PasswordState(DefaultFieldState()))
        private set

    var loadingState by mutableStateOf(SignUpState())
        private set

    private val _uiEvent = Channel<UiEvent<Any>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnCropImage -> {
                profileImageState = event.uri
            }
            is SignUpEvent.OnPickPhoto -> {
                profileImageState = event.uri
            }
            is SignUpEvent.OnEnterUserName -> {
                usernameState = usernameState.copy(
                    text = event.name,
                    isHintVisible = false,
                )
            }
            is SignUpEvent.OnEnterEmail -> {
                emailState = emailState.copy(
                    text = event.email,
                    isHintVisible = false
                )
            }
            is SignUpEvent.OnEnterPassword -> {
                passwordState = passwordState.copy(
                    defaultFieldState = DefaultFieldState(text = event.password)
                )
            }
            is SignUpEvent.OnPasswordToggleClick -> {
                passwordState = passwordState.copy(
                    isPasswordHide = !passwordState.isPasswordHide
                )
            }
            is SignUpEvent.OnRegisterClick -> {
                signUpUser()
            }
        }
    }

    private fun signUpUser() {
        viewModelScope.launch {
            loadingState = loadingState.copy(
                isLoading = true
            )
            //Field errors to null at the beginning
            usernameState = usernameState.copy(error = null)
            emailState = emailState.copy(error = null)
            passwordState = passwordState.copy(
                defaultFieldState = DefaultFieldState(
                    text = passwordState.defaultFieldState.text,
                    error = null
                )
            )

            val signUpResult = userCases.signUpUseCase(
                profileImageState,
                usernameState.text.trim(),
                emailState.text.trim(),
                passwordState.defaultFieldState.text.trim()
            )

            //Fields validation
            if (signUpResult.profileImageError != null) {
                profileImageState = null
                _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.profile_image_not_empty)))
            }

            if (signUpResult.usernameError != null) {
                usernameState = usernameState.copy(error = signUpResult.usernameError)
            }

            if (signUpResult.emailError != null) {
                emailState = emailState.copy(error = signUpResult.emailError)
            }

            if (signUpResult.passwordError != null) {
                passwordState =
                    passwordState.copy(defaultFieldState = DefaultFieldState(error = signUpResult.passwordError))
            }

            when (signUpResult.result) {
                is Resource.Success -> {
                    loadingState = loadingState.copy(
                        isLoading = false,
                    )
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.sign_up_user_register_success)))
                    _uiEvent.send(UiEvent.NavigateUp(Params(email = emailState.text)))

                    //Clear all errors by generating a new instance of field states
                    usernameState = DefaultFieldState()
                    emailState = DefaultFieldState()
                    passwordState = PasswordState(DefaultFieldState())
                }
                is Resource.Error -> {
                    loadingState = loadingState.copy(
                        isLoading = false
                    )
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(
                            signUpResult.result.text ?: UiText.unknownError()
                        )
                    )
                }
                //If happen field error, result is null
                null -> loadingState = loadingState.copy(isLoading = false)
            }
        }

    }

    data class Params(
        val email: String
    )


}