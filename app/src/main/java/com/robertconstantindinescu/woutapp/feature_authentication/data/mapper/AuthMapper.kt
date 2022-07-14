package com.robertconstantindinescu.woutapp.feature_authentication.data.mapper

import com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.response.AuthResponseDto
import com.robertconstantindinescu.woutapp.feature_authentication.domain.model.AuthModel

fun AuthResponseDto.toAuthModel(): AuthModel {
    return AuthModel(
        userId = userId,
        token = token
    )
}