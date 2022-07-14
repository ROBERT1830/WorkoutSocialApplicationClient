package com.robertconstantindinescu.woutapp.feature_authentication.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.util.ApiResource
import com.robertconstantindinescu.woutapp.core.util.UiEvent
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.feature_authentication.domain.use_case.AuthUseCases
import com.robertconstantindinescu.woutapp.feature_authentication.presentation.util.AuthPasswordState
import com.robertconstantindinescu.woutapp.feature_authentication.presentation.util.AuthStandardFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userCases: AuthUseCases
) : ViewModel() {

    var usernameState by mutableStateOf(AuthStandardFieldState())
        private set

    var emailState by mutableStateOf(AuthStandardFieldState())
        private set

    var passwordState by mutableStateOf(AuthPasswordState(AuthStandardFieldState()))
        private set

    var registerState by mutableStateOf(SignUpState())
        private set

    private val _uiEvent = Channel<UiEvent<Any>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
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
                   authStandardFieldState =  AuthStandardFieldState(text = event.password)
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
            registerState = registerState.copy(
                isLoading = true
            )
            //Field errors to null at the beginning
            usernameState = usernameState.copy(error = null)
            emailState = emailState.copy(error = null)
            passwordState =
                passwordState.copy(authStandardFieldState = AuthStandardFieldState(
                    text = passwordState.authStandardFieldState.text,
                    error = null))

            val signUpResult = userCases.signUpUseCase(
                usernameState.text,
                emailState.text,
                passwordState.authStandardFieldState.text
            )

            //Fields validation
            if (signUpResult.username != null) {
                usernameState = usernameState.copy(error = signUpResult.username)
            }

            if (signUpResult.email != null) {
                emailState = emailState.copy(error = signUpResult.email)
            }

            if (signUpResult.password != null) {
                passwordState =
                    passwordState.copy(authStandardFieldState = AuthStandardFieldState(error = signUpResult.password))
            }

            when(signUpResult.result) {
                is ApiResource.Success -> {
                    registerState = registerState.copy(
                        isLoading = false,
                        isSignUpSuccessful = true
                    )

                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.sign_up_user_register_success)))
                    _uiEvent.send(UiEvent.NavigateUp(Params(email = emailState.text)))

                    //Clear all errors by generating a new instance of field states
                    usernameState = AuthStandardFieldState()
                    emailState = AuthStandardFieldState()
                    passwordState = AuthPasswordState(AuthStandardFieldState())


                }
                is ApiResource.Error -> {
                    registerState = registerState.copy(
                        isLoading = false,
                        isSignUpSuccessful = false,
                        message = signUpResult.result.text?.let { message ->
                            UiText.DynamicString(message) }
                    )
                }
                //If happen field error, result is null
                null -> registerState = registerState.copy(isLoading = false)
            }
        }

    }

    data class Params(
        val email: String
    )


}