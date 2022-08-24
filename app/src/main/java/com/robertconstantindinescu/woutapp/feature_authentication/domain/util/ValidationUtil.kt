package com.robertconstantindinescu.woutapp.feature_authentication.domain.util

import android.net.Uri
import android.util.Patterns
import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.AuthConstants.USERNAME_LENGTH
import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.AuthConstants.USER_PASSWORD

object ValidationUtil {

    fun validateProfileImage(profileImage: Uri?) =
        if (profileImage == null) AuthError.ProfileImageEmpty else null
//        if (profileImage == null) {
//            return AuthError.ProfileImageEmpty
//        }
//        return null


    fun validateUsername(username: String) =
        when {
            username.isBlank() -> AuthError.FieldEmpty
            username.length < USERNAME_LENGTH -> AuthError.FieldShort
            else -> kotlin.run { null }
        }


//        username.trim().apply {
//            if (this.isBlank()) return AuthError.FieldEmpty
//            if (this.length < USERNAME_LENGTH) return AuthError.FieldShort
//        }
//        return null


    fun validateEmail(email: String) =
        when {
            email.isBlank() -> AuthError.FieldEmpty
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> AuthError.InvalidEmail
            else -> kotlin.run { null }
        }

//        email.trim().apply {
//            if (this.isBlank()) {
//                return AuthError.FieldEmpty
//            }
//            if (!Patterns.EMAIL_ADDRESS.matcher(this).matches()) {
//                return AuthError.InvalidEmail
//            }
//        }
//        return null


    fun validatePassword(password: String) =
        when {
            password.isBlank() -> AuthError.FieldEmpty
            !password.any { it.isUpperCase() } || !password.any { it.isDigit() } -> AuthError.InvalidPassword
            password.length < USER_PASSWORD -> AuthError.FieldShort
            else -> kotlin.run { null }
        }

//        password.trim().apply {
//            if (this.isBlank()) {
//                return AuthError.FieldEmpty
//            }
//            if (!this.any { it.isUpperCase() } || !this.any { it.isDigit() }) {
//                return AuthError.InvalidPassword
//            }
//            if (this.length < USER_PASSWORD) {
//                return AuthError.FieldShort
//            }
//            return null
//        }
}