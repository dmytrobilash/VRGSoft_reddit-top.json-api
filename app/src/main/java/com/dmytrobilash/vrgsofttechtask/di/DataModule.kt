package com.dmytrobilash.vrgsofttechtask.di

import com.dmytrobilash.data.network.api.RedditAPIQueries
import com.dmytrobilash.data.network.api.RedditRetrofitBuilder
import com.dmytrobilash.data.repository.DataRepositoryImpl
import com.dmytrobilash.domain.repository.DataRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideRedditService(): RedditAPIQueries {
        return RedditRetrofitBuilder().redditService
    }

    @Provides
    fun provideDataRepository(redditService: RedditAPIQueries): DataRepository {
        return DataRepositoryImpl(redditService)
    }
}