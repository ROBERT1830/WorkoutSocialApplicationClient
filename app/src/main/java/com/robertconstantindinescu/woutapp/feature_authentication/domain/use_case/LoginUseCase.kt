package com.robertconstantindinescu.woutapp.feature_authentication.domain.use_case

import com.robertconstantindinescu.woutapp.feature_authentication.domain.model.UserDataValidation
import com.robertconstantindinescu.woutapp.feature_authentication.domain.repository.AuthRepository
import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.AuthError

class LoginUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String, passsword: String): UserDataValidation {

        if (email.isBlank()) return UserDataValidation(emailError = AuthError.FieldEmpty)
        if (passsword.isBlank()) return UserDataValidation(passwordError = AuthError.FieldEmpty)

        return UserDataValidation(
            result = repository.signInUser(email, passsword)
        )
    }

}