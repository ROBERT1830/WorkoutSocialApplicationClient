package com.robertconstantindinescu.woutapp.feature_authentication.presentation.util

import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.AuthError

data class DefaultFieldState(
    val text: String = "",
    val isHintVisible: Boolean = true,
    val error: AuthError? = null
)
