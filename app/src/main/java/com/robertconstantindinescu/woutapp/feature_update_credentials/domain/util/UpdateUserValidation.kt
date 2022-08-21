package com.robertconstantindinescu.woutapp.feature_update_credentials.domain.util

import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.AuthError

data class UpdateUserValidation(
    val usernameError: AuthError? = null,
    val emailError: AuthError? = null,
    val resultError: DefaultApiResource? = null,
)
