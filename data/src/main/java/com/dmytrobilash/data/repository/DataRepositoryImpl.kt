package com.dmytrobilash.data.repository

import com.dmytrobilash.data.mapper.MapperResponse
import com.dmytrobilash.data.network.api.RedditAPIQueries
import com.dmytrobilash.data.network.model.asModel
import com.dmytrobilash.domain.model.PostModel
import com.dmytrobilash.domain.repository.DataRepository

class DataRepositoryImpl(private val dataSource: RedditAPIQueries) : DataRepository {

    override suspend fun getAllTopPost():  List<PostModel> {
        val response = dataSource.getTopPosts()
        return MapperResponse.toPostModel(response.data.children.map { it.data.asModel() })
    }

    override suspend fun getTopPostPagination(limit: Int, after: String): List<PostModel> {
        val response = dataSource.getTopPostForPagination(limit, after)
        return MapperResponse.toPostModel(response.data.children.map { it.data.asModel() })
    }

}