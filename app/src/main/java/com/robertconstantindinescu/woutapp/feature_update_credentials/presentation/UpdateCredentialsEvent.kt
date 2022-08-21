package com.robertconstantindinescu.woutapp.feature_update_credentials.presentation

import com.robertconstantindinescu.woutapp.feature_authentication.presentation.register.SignUpEvent

sealed class UpdateCredentialsEvent {
    data class OnEnterUserName(val name: String): UpdateCredentialsEvent()
    data class OnEnterEmail(val email: String): UpdateCredentialsEvent()
    object OnUpdateCredentials: UpdateCredentialsEvent()
}
