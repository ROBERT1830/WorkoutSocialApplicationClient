package com.robertconstantindinescu.woutapp.feature_posts.di

import android.app.Application
import androidx.room.Room
import com.robertconstantindinescu.woutapp.core.util.subscriptor.PostSubscriptor
import com.robertconstantindinescu.woutapp.core.util.subscriptor.PostSubscriptorImpl
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.local.FavoritePostDatabase
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.remote.PostApi
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.remote.PostApi.Companion.MAIN_FEED_BASE_URL
import com.robertconstantindinescu.woutapp.feature_posts.data.repository.PostRepositoryImpl
import com.robertconstantindinescu.woutapp.feature_posts.data.util.Constants.POST_FAVORITE_DATABASE_NAME
import com.robertconstantindinescu.woutapp.feature_posts.domain.repository.PostRepository
import com.robertconstantindinescu.woutapp.feature_posts.domain.use_case.*
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
            .baseUrl(MAIN_FEED_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(PostApi::class.java)
    }


    // TODO: Provide database and dao
    @Provides
    @Singleton
    fun provideFavoritePostDatabase(app: Application): FavoritePostDatabase {
        return Room.databaseBuilder(
            app,
            FavoritePostDatabase::class.java,
            POST_FAVORITE_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePostRepository(api: PostApi, db: FavoritePostDatabase): PostRepository {
        return PostRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: PostRepository): PostUseCases {
        return PostUseCases(
            mainFeedGetAllPostsUseCase = MainFeedGetAllPostsUseCase(repository),
            personalGetAllPostsUseCase = PersonalGetAllPostsUseCase(repository),
            toggleFavoritesUseCase = InsertPostIntoFavoritesUseCase(repository),
            deleteFromFavoritesUseCase = DeleteFromFavoritesUseCase(repository),
            getAllFavoritesPostsUseCase = GetAllFavoritesPostsUseCase(repository),
            toggleSubscribtionUseCase = ToggleSubscribtionUseCase(repository),
            getPostDetailsUseCase = GetPostDetailsUseCase(repository),
            deletePostFromRemoteUseCase = DeletePostFromRemoteUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun providePostSubscriptor(): PostSubscriptor = PostSubscriptorImpl()

}