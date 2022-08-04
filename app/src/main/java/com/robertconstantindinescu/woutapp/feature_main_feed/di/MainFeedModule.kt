package com.robertconstantindinescu.woutapp.feature_main_feed.di

import com.robertconstantindinescu.woutapp.feature_main_feed.data.dto.remote.MainFeedApi
import com.robertconstantindinescu.woutapp.feature_main_feed.data.dto.remote.MainFeedApi.Companion.MAIN_FEED_BASE_URL
import com.robertconstantindinescu.woutapp.feature_main_feed.data.repository.MainFeedRepositoryImpl
import com.robertconstantindinescu.woutapp.feature_main_feed.domain.repository.MainFeedRepository
import com.robertconstantindinescu.woutapp.feature_main_feed.domain.use_case.GetAllPostsUseCase
import com.robertconstantindinescu.woutapp.feature_main_feed.domain.use_case.MainFeedUseCases
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
object MainFeedModule {
    @Provides
    @Singleton
    fun provideMainFeedApi(okHttpClient: OkHttpClient): MainFeedApi {
        return Retrofit.Builder()
            .baseUrl(MAIN_FEED_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MainFeedApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMainFeedRepository(api: MainFeedApi): MainFeedRepository {
        return MainFeedRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: MainFeedRepository): MainFeedUseCases {
        return MainFeedUseCases(getAllPostsUseCase = GetAllPostsUseCase(repository))
    }
}