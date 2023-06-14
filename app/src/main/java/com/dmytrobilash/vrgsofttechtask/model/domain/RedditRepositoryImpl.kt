package com.dmytrobilash.vrgsofttechtask.model.domain

import com.dmytrobilash.vrgsofttechtask.model.data.RedditPostModel
import com.dmytrobilash.vrgsofttechtask.model.data.asModel
import com.dmytrobilash.vrgsofttechtask.model.network.RedditInterfaceAPI

class RedditRepositoryImpl(private val redditService: RedditInterfaceAPI) : RedditRepositoryIF {

    override suspend fun getTopPosts(): List<RedditPostModel> {
        val response = redditService.getTopPosts()
        return response.data.children.map { it.data.asModel() }
    }

    override suspend fun getTopPostsForPagination(limit: Int, after: String): List<RedditPostModel> {
        val response = redditService.getTopPostForPagination(limit, after)
        return response.data.children.map { it.data.asModel() }
    }
}