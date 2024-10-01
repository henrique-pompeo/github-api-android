package com.example.feature.repos.di

import com.example.feature.repos.domain.interfaces.ReposRepository
import com.example.feature.repos.iu.viewmodel.ReposViewModel
import dagger.Provides
import javax.inject.Singleton

object ViewModelModule {
    @Provides
    @Singleton
    fun provideViewModel(repository: ReposRepository): ReposViewModel {
        return ReposViewModel(repository)
    }
}