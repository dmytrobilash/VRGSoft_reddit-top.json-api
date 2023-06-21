package com.dmytrobilash.vrgsofttechtask.di

import com.dmytrobilash.domain.repository.DataRepository
import com.dmytrobilash.domain.usecases.GetPostsListUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun providePostsListUseCase(dataRepository: DataRepository): GetPostsListUseCase {
        return GetPostsListUseCase(dataRepository)
    }
}