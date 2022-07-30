package com.robertconstantindinescu.woutapp.feature_create_post.di

import android.content.SharedPreferences
import com.google.gson.Gson
import com.robertconstantindinescu.woutapp.feature_create_post.data.remote.PostApi
import com.robertconstantindinescu.woutapp.feature_create_post.data.remote.PostApi.Companion.POST_BASE_URL
import com.robertconstantindinescu.woutapp.feature_create_post.data.repository.PostRepositoryImpl
import com.robertconstantindinescu.woutapp.feature_create_post.domain.repository.PostRepository
import com.robertconstantindinescu.woutapp.feature_create_post.domain.use_case.CreatePostUseCase
import com.robertconstantindinescu.woutapp.feature_create_post.domain.use_case.PostUseCases
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
object PostModule {

    @Provides
    @Singleton
    fun providePostApi(okHttpClient: OkHttpClient): PostApi {
        return Retrofit.Builder()
            .baseUrl(POST_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(PostApi::class.java)
    }

    @Provides
    @Singleton
    fun providePostRepository(api: PostApi, sharedPreferences: SharedPreferences, gson: Gson): PostRepository {
        return PostRepositoryImpl(api = api, sharedPreferences = sharedPreferences, gson = gson)
    }

    @Provides
    @Singleton
    fun provideUeCases(repository: PostRepository): PostUseCases {
        return PostUseCases(
            createPostUseCase = CreatePostUseCase(repository)
        )
    }
}