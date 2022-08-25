package com.robertconstantindinescu.woutapp.feature_authentication.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.UiEvent
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.feature_authentication.domain.use_case.AuthUseCases
import com.robertconstantindinescu.woutapp.feature_authentication.presentation.util.AuthConstants.EMAIL
import com.robertconstantindinescu.woutapp.feature_authentication.presentation.util.PasswordState
import com.robertconstantindinescu.woutapp.feature_authentication.presentation.util.DefaultFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCases: AuthUseCases
) : ViewModel() {

    var emailState by mutableStateOf(DefaultFieldState())
        private set

    var passwordState by mutableStateOf(PasswordState(DefaultFieldState()))
        private set

    var loginState by mutableStateOf(LoginState())
        private set

    private val _uiEvent = Channel<UiEvent<Any>>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        emailState = emailState.copy(
            text = savedStateHandle.get<String>(EMAIL) ?: ""
        )
    }

    fun onEvent(event: LoginEvent) {

        when (event) {
            is LoginEvent.OnEnterEmail -> {
                emailState = emailState.copy(
                    text = event.email
                )
            }
            is LoginEvent.OnEnterPassword -> {
                passwordState = passwordState.copy(
                    defaultFieldState = DefaultFieldState(text = event.password)
                )
            }
            is LoginEvent.OnPasswordToggleClick -> {
                passwordState = passwordState.copy(
                    isPasswordHide = !passwordState.isPasswordHide
                )
            }
            is LoginEvent.OnLoginClick -> {
                loginUser()
            }
        }

    }

    private fun loginUser() {
        viewModelScope.launch {
            loginState = loginState.copy(
                isLoading = true
            )

            emailState = emailState.copy(error = null)
            passwordState = passwordState.copy(
                defaultFieldState = DefaultFieldState(
                    text = passwordState.defaultFieldState.text,
                    error = null
                )
            )

            val loginResult = useCases.signInUseCase(
                email = emailState.text.trim(),
                passsword = passwordState.defaultFieldState.text.trim()
            )

            if (loginResult.emailError != null) {
                emailState = emailState.copy(error = loginResult.emailError)
            }
            if (loginResult.passwordError != null) {
                passwordState =
                    passwordState.copy(defaultFieldState = DefaultFieldState(error = loginResult.passwordError))
            }

            loginResult.result?.mapResourceData(
                success = {
                    loginState = loginState.copy(
                        isLoading = false
                    )
                    //reset fields
                    emailState = DefaultFieldState()
                    passwordState = PasswordState(defaultFieldState = DefaultFieldState())

                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.login_screen_successful_login)))
                    _uiEvent.send(UiEvent.NavigateTo())
                },
                error = { text, _ ->
                    loginState = loginState.copy(isLoading = false)
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(text ?: UiText.unknownError())
                    )
                }
            )

//            when(loginResult.result) {
//                is Resource.Success -> {
//                    loginState = loginState.copy(
//                        isLoading = false
//                    )
//                    //reset fields
//                    emailState = DefaultFieldState()
//                    passwordState = PasswordState(defaultFieldState = DefaultFieldState())
//
//                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.login_screen_successful_login)))
//                    _uiEvent.send(UiEvent.NavigateTo())
//                }
//                is Resource.Error -> {
//                    loginState = loginState.copy(
//                        isLoading = false
//                    )
//                    _uiEvent.send(
//                        UiEvent.ShowSnackBar(loginResult.result.text ?: UiText.unknownError())
//                    )
//                }
//                else -> Unit
//            }


        }
    }
}