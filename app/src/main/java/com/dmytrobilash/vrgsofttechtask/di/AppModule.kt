package com.dmytrobilash.vrgsofttechtask.di

import com.dmytrobilash.domain.usecases.GetPostsListUseCase
import com.dmytrobilash.vrgsofttechtask.viewmodel.RedditPostsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideRedditPostsViewModelFactory(getPostsListUseCase: GetPostsListUseCase): RedditPostsViewModelFactory{
        return RedditPostsViewModelFactory(getPostsListUseCase)
    }
}