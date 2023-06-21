package com.dmytrobilash.vrgsofttechtask.di

import com.dmytrobilash.data.network.api.RedditAPIQueries
import com.dmytrobilash.data.repository.DataRepositoryImpl
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