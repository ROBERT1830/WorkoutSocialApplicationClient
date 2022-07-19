package com.robertconstantindinescu.woutapp.feature_authentication.presentation.login

import com.robertconstantindinescu.woutapp.core.util.UiText

data class LoginState(
    val message: UiText? = null,
    val isLoading: Boolean = false
)
