package com.robertconstantindinescu.woutapp.feature_authentication.domain.use_case

import com.robertconstantindinescu.woutapp.feature_authentication.domain.model.SignUpUserValidation
import com.robertconstantindinescu.woutapp.feature_authentication.domain.repository.AuthRepository
import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.ValidationUtil

class SignUpUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        username: String,
        email: String,
        password: String
    ):SignUpUserValidation {

        val usernameError =  ValidationUtil.validateUsername(username)
        val emailError = ValidationUtil.validateEmail(email)
        val passwordError = ValidationUtil.validatePassword(password)

        if (usernameError != null || emailError != null || passwordError != null) {
            return SignUpUserValidation(
                username = usernameError,
                email = emailError,
                password = passwordError
            )
        }

        return SignUpUserValidation(
            result = repository.signUpUser(username.trim(), email.trim(), password.trim())
        )

    }
}