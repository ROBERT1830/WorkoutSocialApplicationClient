package com.robertconstantindinescu.woutapp.feature_authentication.domain.use_case

data class AuthUseCases(
    val signUpUseCase: SignUpUseCase,
    val signInUseCase: LoginUseCase,
    val initAuthUseCase: InitAuthUseCase
)
