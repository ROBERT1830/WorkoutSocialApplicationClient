package com.robertconstantindinescu.woutapp.feature_authentication.di

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.SHARED_PREF_NAME
import com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.AuthApi
import com.robertconstantindinescu.woutapp.feature_authentication.data.remote.dto.AuthApi.Companion.AUT_BASE_URL
import com.robertconstantindinescu.woutapp.feature_authentication.data.repository.AuthRepositoryImpl
import com.robertconstantindinescu.woutapp.feature_authentication.domain.repository.AuthRepository
import com.robertconstantindinescu.woutapp.feature_authentication.domain.use_case.AuthUseCases
import com.robertconstantindinescu.woutapp.feature_authentication.domain.use_case.InitAuthUseCase
import com.robertconstantindinescu.woutapp.feature_authentication.domain.use_case.LoginUseCase
import com.robertconstantindinescu.woutapp.feature_authentication.domain.use_case.SignUpUseCase
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
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthenticationApi(client: OkHttpClient): AuthApi {
        return Retrofit.Builder()
            .baseUrl(AUT_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(AuthApi::class.java)
    }



    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi, sharedPreferences: SharedPreferences): AuthRepository {
        return AuthRepositoryImpl(api, sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSignUpUseCase(repository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            signUpUseCase = SignUpUseCase(repository),
            signInUseCase = LoginUseCase(repository),
            initAuthUseCase = InitAuthUseCase(repository)
        )
    }

}