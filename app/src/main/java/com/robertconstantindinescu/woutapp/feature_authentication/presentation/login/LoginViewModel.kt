package com.robertconstantindinescu.woutapp.feature_authentication.presentation.login

import android.graphics.text.TextRunShaper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
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
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCases: AuthUseCases
) : ViewModel() {

    var emailState by mutableStateOf(AuthStandardFieldState())
        private set
    var passwordState by mutableStateOf(AuthPasswordState(AuthStandardFieldState()))
        private set

    var loginState by mutableStateOf(LoginState())
        private set

    private val _uiEvent = Channel<UiEvent<Any>>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        emailState = emailState.copy(
            text = savedStateHandle.get<String>("email") ?: ""
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
                    authStandardFieldState = AuthStandardFieldState(text = event.password)
                )
            }
            is LoginEvent.OnPasswordToggleClick -> {
                passwordState = passwordState.copy(
                    isPasswordHide = !passwordState.isPasswordHide
                )
            }
            is LoginEvent.onLoginClick -> {
                loginUser()
            }
            is LoginEvent.onSignUpClick -> {

            }
        }

    }

    private fun loginUser() {
        viewModelScope.launch {
            loginState = loginState.copy(
                isLoginSuccessful = false,
                isLoading = true
            )

            emailState = emailState.copy(error = null)
            passwordState = passwordState.copy(
                authStandardFieldState = AuthStandardFieldState(
                    text = passwordState.authStandardFieldState.text,
                    error = null
                )
            )

            val loginResult = useCases.signInUseCase(
                email = emailState.text,
                passsword = passwordState.authStandardFieldState.text
            )

            if (loginResult.email != null) {
                emailState = emailState.copy(error = loginResult.email)
            }
            if (loginResult.password != null) {
                passwordState =
                    passwordState.copy(authStandardFieldState = AuthStandardFieldState(error = loginResult.password))
            }

            when(loginResult.result) {
                is ApiResource.Success -> {
                    loginState = loginState.copy(
                        isLoginSuccessful = true,
                        isLoading = false
                    )
                    emailState = AuthStandardFieldState()
                    passwordState = AuthPasswordState(authStandardFieldState = AuthStandardFieldState())
                    // TODO: emit message of succesflully login
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.login_screen_successful_login)))
                    _uiEvent.send(UiEvent.NavigateTo()) //review to nos leave likea  class...is think
                }
                is ApiResource.Error -> {
                    loginState = loginState.copy(
                        isLoginSuccessful = false,
                        isLoading = false
                    )



                }
            }


        }
    }
}