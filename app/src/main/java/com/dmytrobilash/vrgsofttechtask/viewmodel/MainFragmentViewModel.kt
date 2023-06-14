package com.dmytrobilash.vrgsofttechtask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmytrobilash.vrgsofttechtask.model.data.RedditPostModel
import androidx.lifecycle.viewModelScope
import com.dmytrobilash.vrgsofttechtask.model.domain.RedditRepositoryIF
import kotlinx.coroutines.launch

class MainFragmentViewModel(private val redditRepository: RedditRepositoryIF) : ViewModel() {


    private val _posts = MutableLiveData<List<RedditPostModel>>()
    val posts: LiveData<List<RedditPostModel>> = _posts

    init {
        fetchPosts()
    }

    //get starter posts(25 items)
    private fun fetchPosts() {
        viewModelScope.launch {
            try {
                val posts = redditRepository.getTopPosts()
                _posts.value = posts
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

    fun fetchPostsForPagination(limit: Int, after: String) {
        viewModelScope.launch {
            try {
                val posts = redditRepository.getTopPostsForPagination(limit, after)
                _posts.value = _posts.value?.plus(posts)
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }
}