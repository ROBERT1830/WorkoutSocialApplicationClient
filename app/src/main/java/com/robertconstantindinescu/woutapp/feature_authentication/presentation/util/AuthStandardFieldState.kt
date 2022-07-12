package com.robertconstantindinescu.woutapp.feature_authentication.presentation.util

import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.AuthError

data class AuthStandardFieldState(
    val text: String = "",
    val isHintVisible: Boolean = true,
    val error: AuthError? = null
)
