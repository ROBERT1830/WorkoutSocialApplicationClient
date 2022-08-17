package com.robertconstantindinescu.woutapp.feature_posts.di

import android.app.Application
import androidx.room.Room
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.local.FavoritePostDatabase
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.local.entities.FavoritesPostDao
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.remote.MainFeedApi
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.remote.MainFeedApi.Companion.MAIN_FEED_BASE_URL
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
    fun provideMainFeedRepository(api: MainFeedApi, db: FavoritePostDatabase): PostRepository {
        return PostRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: PostRepository): PostUseCases {
        return PostUseCases(
            mainFeedGetAllPostsUseCase = MainFeedGetAllPostsUseCase(repository),
            personalGetAllPostsUseCase = PersonalGetAllPostsUseCase(repository),
            insertPostIntoFavoritesUseCase = InsertPostIntoFavoritesUseCase(repository),
            deleteFromFavoritesUseCase = DeleteFromFavoritesUseCase(repository),
            getAllFavoritesPostsUseCase = GetAllFavoritesPostsUseCase(repository)
        )
    }
}