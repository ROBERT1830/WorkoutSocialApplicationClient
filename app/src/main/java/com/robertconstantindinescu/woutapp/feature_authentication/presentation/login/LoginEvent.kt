package com.robertconstantindinescu.woutapp.feature_authentication.presentation.login

sealed class LoginEvent {
    data class OnEnterEmail(val email: String) : LoginEvent()
    data class OnEnterPassword(val password: String) : LoginEvent()
    object OnPasswordToggleClick : LoginEvent()
    object OnLoginClick : LoginEvent()
}
