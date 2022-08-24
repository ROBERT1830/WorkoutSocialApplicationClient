package com.robertconstantindinescu.woutapp.feature_authentication.domain.util

sealed class AuthError {
    object FieldEmpty : AuthError()
    object FieldShort : AuthError()
    object InvalidEmail : AuthError()
    object InvalidPassword : AuthError()
    object ProfileImageEmpty: AuthError()
}
