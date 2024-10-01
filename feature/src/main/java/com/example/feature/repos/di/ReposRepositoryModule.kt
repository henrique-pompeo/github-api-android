package com.example.feature.repos.di

import com.example.feature.repos.data.mapper.ReposMapper
import com.example.feature.repos.data.repository.ReposRepositoryImpl
import com.example.feature.repos.data.service.ReposService
import com.example.feature.repos.domain.interfaces.ReposRepository
import dagger.Provides
import javax.inject.Singleton

object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(service: ReposService, mapper: ReposMapper): ReposRepository {
        return ReposRepositoryImpl(service, mapper)
    }
}