package com.dmytrobilash.vrgsofttechtask.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmytrobilash.domain.usecases.GetPostsListUseCase
import javax.inject.Inject


class RedditPostsViewModelFactory @Inject constructor(private val getPostsListUseCase: GetPostsListUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RedditPostsViewModel(
            getPostsListUseCase
        ) as T
    }
}