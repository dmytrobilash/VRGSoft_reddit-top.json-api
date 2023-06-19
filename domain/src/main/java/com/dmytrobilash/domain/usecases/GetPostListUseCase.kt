package com.dmytrobilash.domain.usecases

import com.dmytrobilash.domain.repository.DataRepository

class GetPostListUseCase(private val repository: DataRepository) {
    suspend fun execute() = repository.getAllTopPost() //25 posts
}