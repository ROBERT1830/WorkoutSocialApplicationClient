package com.robertconstantindinescu.woutapp.feature_authentication.domain.repository

import android.net.Uri
import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource

interface AuthRepository {

    suspend fun signUpUser(
        profileImage: Uri,
        name: String,
        email: String,
        password: String
    ): DefaultApiResource

    suspend fun signInUser(
        email: String,
        password: String
    ): DefaultApiResource

    suspend fun authenticate(): DefaultApiResource
}