package com.example.feature.repos.di

import com.example.feature.repos.data.mapper.ReposMapper
import dagger.Provides
import javax.inject.Singleton

object MapperModule {
    @Provides
    @Singleton
    fun provideMapper(): ReposMapper {
        return ReposMapper()
    }
}