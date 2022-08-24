package com.robertconstantindinescu.woutapp.feature_authentication.domain.use_case

import android.net.Uri
import com.robertconstantindinescu.woutapp.feature_authentication.domain.model.UserDataValidation
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
    ): UserDataValidation {

        val profileImageError = ValidationUtil.validateProfileImage(profileImage)
        val usernameError =  ValidationUtil.validateUsername(username.trim())
        val emailError = ValidationUtil.validateEmail(email.trim())
        val passwordError = ValidationUtil.validatePassword(password.trim())

        //That way to check them all at once
        if (profileImageError != null || usernameError != null || emailError != null || passwordError != null) {
            return UserDataValidation(
                profileImageError = profileImageError,
                usernameError = usernameError,
                emailError = emailError,
                passwordError = passwordError
            )
        }

        return UserDataValidation(
            resultError = repository.signUpUser(profileImage!!, username, email, password)
        )
    }
}