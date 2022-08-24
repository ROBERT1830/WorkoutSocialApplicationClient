package com.robertconstantindinescu.woutapp.feature_authentication.presentation.util

data class PasswordState(
    val defaultFieldState: DefaultFieldState,
    val isPasswordHide: Boolean = true
)