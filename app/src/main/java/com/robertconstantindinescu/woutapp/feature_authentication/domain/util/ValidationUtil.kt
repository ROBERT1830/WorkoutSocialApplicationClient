package com.robertconstantindinescu.woutapp.feature_authentication.domain.util

import android.net.Uri
import android.util.Patterns
import com.robertconstantindinescu.woutapp.feature_authentication.domain.model.SignUpUserValidation
import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.AuthConstants.USERNAME_LENGTH
import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.AuthConstants.USER_PASSWORD

object ValidationUtil {

    fun validateProfileImage(profileImage: Uri?): AuthError? {
        if (profileImage == null) {
            return AuthError.profileImageEmpty
        }
        return null
    }


    fun validateUsername(username: String): AuthError? {
        username.trim().apply {
            if (this.isBlank()) return AuthError.FieldEmpty
            if (this.length < USERNAME_LENGTH) return AuthError.FieldShort
        }
        return null
    }

    fun validateEmail(email: String): AuthError? {
        email.trim().apply {
            if (this.isBlank()) {
                return AuthError.FieldEmpty
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(this).matches()) {
                return AuthError.InvalidEmail
            }
        }
        return null
    }

    fun validatePassword(password: String): AuthError? {
        password.trim().apply {
            if (this.isBlank()) {
                return AuthError.FieldEmpty
            }
            if (!this.any { it.isUpperCase() } || !this.any { it.isDigit() }) {
                return AuthError.InvalidPassword
            }
            if (this.length < USER_PASSWORD) {
                return AuthError.FieldShort
            }
            return null
        }
    }


}