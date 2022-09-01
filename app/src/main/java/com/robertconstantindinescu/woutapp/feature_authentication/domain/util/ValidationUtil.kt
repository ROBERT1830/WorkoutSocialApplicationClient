package com.robertconstantindinescu.woutapp.feature_authentication.domain.util

import android.net.Uri
import android.util.Patterns
import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.AuthConstants.USERNAME_LENGTH
import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.AuthConstants.USER_PASSWORD

object ValidationUtil {

    fun validateProfileImage(profileImage: Uri?) =
        if (profileImage == null) AuthError.ProfileImageEmpty else null

    fun validateUsername(username: String) =
        when {
            username.isBlank() -> AuthError.FieldEmpty
            username.length < USERNAME_LENGTH -> AuthError.FieldShort
            else -> kotlin.run { null }
        }


    fun validateEmail(email: String) =
        when {
            email.isBlank() -> AuthError.FieldEmpty
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> AuthError.InvalidEmail
            else -> kotlin.run { null }
        }


    fun validatePassword(password: String) =
        when {
            password.isBlank() -> AuthError.FieldEmpty
            !password.any { it.isUpperCase() } || !password.any { it.isDigit() } -> AuthError.InvalidPassword
            password.length < USER_PASSWORD -> AuthError.FieldShort
            else -> kotlin.run { null }
        }
}