package com.dmytrobilash.domain.repository

import com.dmytrobilash.domain.model.RedditPostModelDomain

interface DataRepository {
    suspend fun getAllTopPost(): List<RedditPostModelDomain>
    suspend fun getTopPostPagination(limit: Int, after: String): List<RedditPostModelDomain>
}