package com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.request

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String
)
