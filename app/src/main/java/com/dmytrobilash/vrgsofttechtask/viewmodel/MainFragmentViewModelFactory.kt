package com.dmytrobilash.vrgsofttechtask.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmytrobilash.data.repository.DataRepositoryImpl
import com.dmytrobilash.data.network.api.RedditRetrofitBuilder
import com.dmytrobilash.domain.usecases.GetPostsListUseCase



class MainFragmentViewModelFactory : ViewModelProvider.Factory {

    private val repository = DataRepositoryImpl(RedditRetrofitBuilder().redditService)
    private val getPostsListUseCase = GetPostsListUseCase(repository)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainFragmentViewModel(
            getPostsListUseCase
        ) as T
    }
}