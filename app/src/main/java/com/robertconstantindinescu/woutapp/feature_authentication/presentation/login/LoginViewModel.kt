package com.robertconstantindinescu.woutapp.feature_authentication.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.robertconstantindinescu.woutapp.feature_authentication.presentation.util.AuthPasswordState
import com.robertconstantindinescu.woutapp.feature_authentication.presentation.util.AuthStandardFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var emailState by mutableStateOf(AuthStandardFieldState())
        private set
    var passwordState by mutableStateOf(AuthPasswordState(AuthStandardFieldState()))
        private set


    init {
        emailState = emailState.copy(
            text = savedStateHandle.get<String>("email") ?: ""
        )
    }

    fun onEvent(event: LoginEvent) {

         when(event) {
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
             is LoginEvent.onLoginClick -> {

             }
             is LoginEvent.onSignUpClick -> {

             }
         }

    }
}