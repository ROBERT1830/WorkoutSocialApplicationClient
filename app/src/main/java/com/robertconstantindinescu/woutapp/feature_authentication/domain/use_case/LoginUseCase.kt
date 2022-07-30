package com.robertconstantindinescu.woutapp.feature_authentication.domain.use_case

import com.robertconstantindinescu.woutapp.feature_authentication.domain.model.LoginUserValidation
import com.robertconstantindinescu.woutapp.feature_authentication.domain.repository.AuthRepository
import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.AuthError
import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.ValidationUtil

class LoginUseCase(private val repository: AuthRepository) {

    // TODO: mirar bien bnien el tema del; resource.  
    suspend operator fun invoke(email: String, passsword: String): LoginUserValidation {

        if (email.trim().isBlank()) return LoginUserValidation(email = AuthError.FieldEmpty)
        if (passsword.trim().isBlank()) return LoginUserValidation(password = AuthError.FieldEmpty)

        return LoginUserValidation(
            result = repository.signInUser(email.trim(), passsword.trim())
        )
    }

}