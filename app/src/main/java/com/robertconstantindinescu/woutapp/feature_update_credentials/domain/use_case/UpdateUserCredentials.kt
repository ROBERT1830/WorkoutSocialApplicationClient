package com.robertconstantindinescu.woutapp.feature_update_credentials.domain.use_case

import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.feature_authentication.domain.model.SignUpUserValidation
import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.ValidationUtil
import com.robertconstantindinescu.woutapp.feature_update_credentials.domain.repository.UpdateProfileRepository
import com.robertconstantindinescu.woutapp.feature_update_credentials.domain.util.UpdateUserValidation

class UpdateUserCredentials(private val repository: UpdateProfileRepository) {

    suspend operator fun invoke(username: String, email: String): UpdateUserValidation  {

        val usernameError =  ValidationUtil.validateUsername(username)
        val emailError = ValidationUtil.validateEmail(email)

        if (usernameError != null || emailError != null) {
            return UpdateUserValidation(
                usernameError = usernameError,
                emailError = emailError
            )
        }
        return UpdateUserValidation(
            resultError = repository.updateUserCredentials(username.trim(), email.trim())
        )

    }

}