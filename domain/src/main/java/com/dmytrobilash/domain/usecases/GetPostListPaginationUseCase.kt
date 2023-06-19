package com.dmytrobilash.domain.usecases

import com.dmytrobilash.domain.repository.DataRepository

class GetPostListPaginationUseCase(private val repository: DataRepository) {
    suspend fun execute(limit: Int, after: String) = repository.getTopPostPagination(limit, after) //25 posts
}