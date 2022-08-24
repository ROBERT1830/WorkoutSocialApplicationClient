package com.robertconstantindinescu.woutapp.feature_update_credentials.domain.use_case

import com.robertconstantindinescu.woutapp.feature_authentication.domain.model.UserDataValidation
import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.ValidationUtil
import com.robertconstantindinescu.woutapp.feature_update_credentials.domain.repository.UpdateProfileRepository

class UpdateUserCredentials(private val repository: UpdateProfileRepository) {

    suspend operator fun invoke(username: String, email: String): UserDataValidation  {

        val usernameError =  ValidationUtil.validateUsername(username)
        val emailError = ValidationUtil.validateEmail(email)

        if (usernameError != null || emailError != null) {
            return UserDataValidation(
                usernameError = usernameError,
                emailError = emailError
            )
        }
        return UserDataValidation(
            result = repository.updateUserCredentials(username.trim(), email.trim())
        )
    }

}