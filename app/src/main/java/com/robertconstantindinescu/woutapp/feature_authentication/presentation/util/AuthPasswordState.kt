package com.robertconstantindinescu.woutapp.feature_authentication.presentation.util

data class AuthPasswordState(
    val authStandardFieldState: AuthStandardFieldState,
    val isPasswordShown: Boolean = false
)