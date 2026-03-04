package com.example.highlevelarch.di

import android.content.Context
import androidx.room.Room
import com.example.highlevelarch.data.local.AppDatabase
import com.example.highlevelarch.data.local.PostDao
import com.example.highlevelarch.data.remote.PostApiService
import com.example.highlevelarch.data.repository.PostRepositoryImpl
import com.example.highlevelarch.domain.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "posts-db"
        ).build()
    }

    @Provides
    fun providePostDao(database: AppDatabase): PostDao = database.postDao()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(PostApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePostApiService(retrofit: Retrofit): PostApiService = retrofit.create(PostApiService::class.java)

    @Provides
    @Singleton
    fun providePostRepository(impl: PostRepositoryImpl): PostRepository = impl
}
