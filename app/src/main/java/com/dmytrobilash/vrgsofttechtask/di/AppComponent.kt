package com.dmytrobilash.vrgsofttechtask.di

import com.dmytrobilash.vrgsofttechtask.view.RedditPostsFragment
import dagger.Component

@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    fun inject(fragment: RedditPostsFragment)
}