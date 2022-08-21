package com.robertconstantindinescu.woutapp.feature_update_credentials.presentation.mapper

import com.robertconstantindinescu.woutapp.feature_update_credentials.domain.model.UpdateCredentialDM
import com.robertconstantindinescu.woutapp.feature_update_credentials.presentation.model.UpdateCredentialVO

fun UpdateCredentialDM.toUpdateCredentialVO() : UpdateCredentialVO =
    UpdateCredentialVO(name = name, email = email)