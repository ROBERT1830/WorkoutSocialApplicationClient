package com.robertconstantindinescu.woutapp.feature_authentication.domain.model

import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.AuthError

data class UserDataValidation(
    val profileImageError: AuthError? = null,
    val usernameError: AuthError? = null,
    val emailError: AuthError? = null,
    val passwordError: AuthError? = null,
    val resultError: DefaultApiResource? = null,
)
