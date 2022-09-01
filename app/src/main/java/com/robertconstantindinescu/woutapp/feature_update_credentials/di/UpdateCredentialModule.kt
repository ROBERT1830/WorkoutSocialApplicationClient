package com.robertconstantindinescu.woutapp.feature_update_credentials.di

import com.robertconstantindinescu.woutapp.feature_update_credentials.data.remote.UpdateCredentialApi
import com.robertconstantindinescu.woutapp.feature_update_credentials.data.remote.UpdateCredentialApi.Companion.UPDATE_CREDENTIALS_BASE_URL
import com.robertconstantindinescu.woutapp.feature_update_credentials.domain.repository.UpdateProfileRepository
import com.robertconstantindinescu.woutapp.feature_update_credentials.domain.use_case.GetUserCredentialsUseCase
import com.robertconstantindinescu.woutapp.feature_update_credentials.domain.use_case.UpdateCredentialsUseCases
import com.robertconstantindinescu.woutapp.feature_update_credentials.domain.use_case.UpdateUserCredentials
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UpdateCredentialModule {

    @Provides
    @Singleton
    fun provideUpdateCredentialApi(okHttpClient: OkHttpClient): UpdateCredentialApi {
        return Retrofit.Builder()
            .baseUrl(UPDATE_CREDENTIALS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(UpdateCredentialApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUpdateCredentialRepository(api: UpdateCredentialApi): UpdateProfileRepository {
        return com.robertconstantindinescu.woutapp.feature_update_credentials.data.repository.UpdateProfileRepository(api)
    }

    @Provides
    @Singleton
    fun provideUpdateProfileUseCase(repository: UpdateProfileRepository): UpdateCredentialsUseCases {
        return UpdateCredentialsUseCases(
            getUserCredentialsUseCase = GetUserCredentialsUseCase(repository),
            updateCredentialsUseCases = UpdateUserCredentials(repository)
        )
    }

}