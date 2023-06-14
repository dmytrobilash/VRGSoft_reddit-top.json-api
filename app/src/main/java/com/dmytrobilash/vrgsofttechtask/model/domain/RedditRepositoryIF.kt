package com.dmytrobilash.vrgsofttechtask.model.domain

import com.dmytrobilash.vrgsofttechtask.model.data.RedditPostModel

interface RedditRepositoryIF {
    suspend fun getTopPosts(): List<RedditPostModel>
    suspend fun getTopPostsForPagination(limit: Int, after: String): List<RedditPostModel>
}