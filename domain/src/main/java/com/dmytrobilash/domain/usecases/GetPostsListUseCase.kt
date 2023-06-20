package com.dmytrobilash.domain.usecases

import com.dmytrobilash.domain.repository.DataRepository

class GetPostsListUseCase(private val repository: DataRepository) {
    suspend fun execute() = repository.getAllTopPost() //25 posts
    suspend fun execute(limit: Int, after: String) = repository.getTopPostPagination(limit, after) //25 posts
}