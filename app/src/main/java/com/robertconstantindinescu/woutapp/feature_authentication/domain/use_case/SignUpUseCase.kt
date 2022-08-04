package com.robertconstantindinescu.woutapp.feature_authentication.domain.use_case

import android.net.Uri
import com.robertconstantindinescu.woutapp.feature_authentication.domain.model.SignUpUserValidation
import com.robertconstantindinescu.woutapp.feature_authentication.domain.repository.AuthRepository
import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.ValidationUtil

class SignUpUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        profileImage: Uri?,
        username: String,
        email: String,
        password: String
    ): SignUpUserValidation {

        val profileImageError = ValidationUtil.validateProfileImage(profileImage)
        val usernameError =  ValidationUtil.validateUsername(username)
        val emailError = ValidationUtil.validateEmail(email)
        val passwordError = ValidationUtil.validatePassword(password)


        if (profileImageError != null || usernameError != null || emailError != null || passwordError != null) {
            return SignUpUserValidation(
                usernameError = usernameError,
                emailError = emailError,
                passwordError = passwordError
            )
        }

        return SignUpUserValidation(
            resultError = repository.signUpUser(profileImage!! ,username.trim(), email.trim(), password.trim())
        )

    }
}