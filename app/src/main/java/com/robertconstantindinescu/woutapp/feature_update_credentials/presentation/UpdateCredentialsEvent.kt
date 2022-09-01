package com.robertconstantindinescu.woutapp.feature_update_credentials.presentation

sealed class UpdateCredentialsEvent {
    data class OnEnterUserName(val name: String): UpdateCredentialsEvent()
    data class OnEnterEmail(val email: String): UpdateCredentialsEvent()
    object OnUpdateCredentials: UpdateCredentialsEvent()
}
