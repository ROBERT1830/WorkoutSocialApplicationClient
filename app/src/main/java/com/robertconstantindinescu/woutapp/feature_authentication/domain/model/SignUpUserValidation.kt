package com.robertconstantindinescu.woutapp.feature_authentication.domain.model

import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.AuthError

data class SignUpUserValidation(
    val username: AuthError? = null,
    val email: AuthError? = null,
    val password: AuthError? = null,
    val result: DefaultApiResource? = null,
)
