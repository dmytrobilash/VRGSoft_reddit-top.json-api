package com.dmytrobilash.data.repository

import com.dmytrobilash.data.mapper.MapperResponse
import com.dmytrobilash.data.network.api.RedditAPIQueries
import com.dmytrobilash.data.network.api.RedditRetrofitBuilder
import com.dmytrobilash.domain.model.PostModel
import com.dmytrobilash.domain.repository.DataRepository

class DataRepositoryImpl(private val dataSource: RedditAPIQueries) : DataRepository {

    override suspend fun getAllTopPost(): List<PostModel> {
        return MapperResponse.toPostModel(dataSource.getTopPosts())
    }

    override suspend fun getTopPostPagination(limit: Int, after: String): List<PostModel> {
        return MapperResponse.toPostModel(dataSource.getTopPostForPagination(limit, after))
    }
}