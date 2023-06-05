package com.dmytrobilash.vrgsofttechtask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmytrobilash.vrgsofttechtask.model.domain.RedditPostModel
import com.dmytrobilash.vrgsofttechtask.model.domain.asModel
import com.dmytrobilash.vrgsofttechtask.model.network.RedditImplAPI
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainFragmentViewModel : ViewModel() {

    private val reddit = RedditImplAPI().redditService
    private val _posts = MutableLiveData<List<RedditPostModel>>()
    val posts: LiveData<List<RedditPostModel>> = _posts

    init {
        fetchPosts()
    }

    //get starter posts(25 items)
    private fun fetchPosts() {
        viewModelScope.launch {
            try {
                val response = reddit.getTopPosts()
                val posts = response.data.children.map {
                    it.data.asModel()
                }
                _posts.value = posts
            } catch (e: Exception) {

            }
        }
    }

    //get pagination posts
    fun fetchPostsForPagination(limit: Int, after: String) {
        viewModelScope.launch {
            try {
                val response = reddit.getTopPostForPagination(limit, after)
                val posts = response.data.children.map {
                    it.data.asModel()
                }
                _posts.value = _posts.value?.plus(posts) //old items + new items from this response
            } catch (e: Exception) {

            }
        }
    }
}