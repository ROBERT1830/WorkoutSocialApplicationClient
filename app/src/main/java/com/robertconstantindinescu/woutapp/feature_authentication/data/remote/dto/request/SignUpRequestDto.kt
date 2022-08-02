package com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.request

import android.net.Uri

data class SignUpRequestDto(
    val name: String,
    val email: String,
    val password: String
)
