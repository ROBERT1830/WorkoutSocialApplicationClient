package com.robertconstantindinescu.woutapp.feature_authentication.presentation.register

import com.robertconstantindinescu.woutapp.core.util.UiText

data class SignUpState(
    val isSignUpSuccessful: Boolean = false,
    val message: UiText? = null,
    val isLoading: Boolean = false
)
