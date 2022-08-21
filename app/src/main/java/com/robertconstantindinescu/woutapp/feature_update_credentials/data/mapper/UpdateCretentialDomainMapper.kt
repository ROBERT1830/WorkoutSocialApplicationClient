package com.robertconstantindinescu.woutapp.feature_update_credentials.data.mapper

import com.robertconstantindinescu.woutapp.feature_update_credentials.data.remote.dto.request.UpdateCredentialDto
import com.robertconstantindinescu.woutapp.feature_update_credentials.domain.model.UpdateCredentialDM

fun UpdateCredentialDto.toUpdateCredentialDM() : UpdateCredentialDM =
    UpdateCredentialDM(name = name, email = email)