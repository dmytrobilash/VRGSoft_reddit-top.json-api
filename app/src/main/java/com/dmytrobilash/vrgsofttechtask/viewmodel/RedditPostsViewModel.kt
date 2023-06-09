package com.dmytrobilash.vrgsofttechtask.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmytrobilash.vrgsofttechtask.model.RedditPostUIModel
import com.dmytrobilash.domain.usecases.GetPostsListUseCase
import com.dmytrobilash.vrgsofttechtask.model.mapper.MapperDomainToUI
import kotlinx.coroutines.launch

class RedditPostsViewModel (private val getPostsUseCase: GetPostsListUseCase) : ViewModel() {

    private val _posts = MutableLiveData<List<RedditPostUIModel>>()
    val posts: LiveData<List<RedditPostUIModel>> = _posts

    init {
        fetchPosts()
    }

    //get starter posts(25 items)
    private fun fetchPosts() {
        viewModelScope.launch {
            try {
                val posts = getPostsUseCase.execute()
                _posts.value = MapperDomainToUI.toPostUIModel(posts)
            } catch (e: Exception) {
                e.printStackTrace() //shiza progresiruet
                Log.e("IO", "IO$e")
            }
        }
    }

    fun fetchPostsForPagination(limit: Int, after: String) {
        viewModelScope.launch {
            try {
                val posts = getPostsUseCase.execute(limit, after)
                _posts.value = _posts.value?.plus(MapperDomainToUI.toPostUIModel(posts))
            } catch (e: Exception) {
                e.printStackTrace() //shiza progresiruet
                Log.e("IO", "IO$e")
            }
        }
    }
}