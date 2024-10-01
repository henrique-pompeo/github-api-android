package com.example.githubapiandroid.di

import com.example.feature.pulls.data.service.PullsService
import com.example.feature.repos.data.service.ReposService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideReposService(retrofit: Retrofit): ReposService =
        retrofit.create(ReposService::class.java)

    @Provides
    fun providePullsService(retrofit: Retrofit): PullsService =
        retrofit.create(PullsService::class.java)
}
