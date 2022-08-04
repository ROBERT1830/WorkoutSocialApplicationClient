package com.robertconstantindinescu.woutapp.feature_authentication.data.mapper

import com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.response.AuthResponseDto
import com.robertconstantindinescu.woutapp.feature_authentication.domain.model.AuthDM

fun AuthResponseDto.toAuthModel(): AuthDM {
    return AuthDM(
        userId = userId,
        token = token
    )
}