package com.robertconstantindinescu.woutapp.feature_authentication.presentation.register

sealed class SignUpEvent {
    data class OnEnterUserName(val name: String): SignUpEvent()
    data class OnEnterEmail(val email: String): SignUpEvent()
    data class OnEnterPassword(val password: String): SignUpEvent()
    object OnPasswordToggleClick : SignUpEvent()
    object OnRegisterClick : SignUpEvent()
}
