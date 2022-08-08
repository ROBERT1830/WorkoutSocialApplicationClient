package com.robertconstantindinescu.woutapp.feature_get_posts.di

import android.content.SharedPreferences
import com.robertconstantindinescu.woutapp.feature_get_posts.data.dto.remote.MainFeedApi
import com.robertconstantindinescu.woutapp.feature_get_posts.data.dto.remote.MainFeedApi.Companion.MAIN_FEED_BASE_URL
import com.robertconstantindinescu.woutapp.feature_get_posts.data.repository.PostRepositoryImpl
import com.robertconstantindinescu.woutapp.feature_get_posts.domain.repository.PostRepository
import com.robertconstantindinescu.woutapp.feature_get_posts.domain.use_case.MainFeedGetAllPostsUseCase
import com.robertconstantindinescu.woutapp.feature_get_posts.domain.use_case.PersonalGetAllPostsUseCase
import com.robertconstantindinescu.woutapp.feature_get_posts.domain.use_case.PostUseCases
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
    fun provideMainFeedRepository(api: MainFeedApi): PostRepository {
        return PostRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: PostRepository): PostUseCases {
        return PostUseCases(
            mainFeedGetAllPostsUseCase = MainFeedGetAllPostsUseCase(repository),
            personalGetAllPostsUseCase = PersonalGetAllPostsUseCase(repository)
        )
    }
}