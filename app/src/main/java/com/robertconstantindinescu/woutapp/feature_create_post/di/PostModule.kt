package com.robertconstantindinescu.woutapp.feature_create_post.di

import com.google.gson.Gson
import com.robertconstantindinescu.woutapp.feature_create_post.data.remote.PostApi
import com.robertconstantindinescu.woutapp.feature_create_post.data.remote.PostApi.Companion.POST_BASE_URL
import com.robertconstantindinescu.woutapp.feature_create_post.data.repository.CreatePostRepositoryImpl
import com.robertconstantindinescu.woutapp.feature_create_post.domain.repository.CreatePostRepository
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
    fun providePostRepository(api: PostApi, gson: Gson): CreatePostRepository {
        return CreatePostRepositoryImpl(api = api, gson = gson)
    }

    @Provides
    @Singleton
    fun provideUeCases(repositoryCreate: CreatePostRepository): PostUseCases {
        return PostUseCases(
            createPostUseCase = CreatePostUseCase(repositoryCreate)
        )
    }
}