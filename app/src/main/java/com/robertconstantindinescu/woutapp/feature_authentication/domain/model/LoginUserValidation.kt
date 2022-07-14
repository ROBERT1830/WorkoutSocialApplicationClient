package com.robertconstantindinescu.woutapp.feature_authentication.domain.model

import com.robertconstantindinescu.woutapp.core.util.ApiResource
import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.AuthError

data class LoginUserValidation(
    val email: AuthError? = null,
    val password: AuthError? = null,
    val result: ApiResource<AuthModel>? = null
)
