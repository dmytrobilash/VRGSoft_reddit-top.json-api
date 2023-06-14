package com.dmytrobilash.vrgsofttechtask.di

import com.dmytrobilash.vrgsofttechtask.model.network.RedditImplAPI
import com.dmytrobilash.vrgsofttechtask.model.domain.RedditRepositoryImpl
import com.dmytrobilash.vrgsofttechtask.model.domain.RedditRepositoryIF
import com.dmytrobilash.vrgsofttechtask.viewmodel.MainFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { RedditImplAPI().redditService }
    single<RedditRepositoryIF> { RedditRepositoryImpl(get()) }
    viewModel { MainFragmentViewModel(get()) }
}
