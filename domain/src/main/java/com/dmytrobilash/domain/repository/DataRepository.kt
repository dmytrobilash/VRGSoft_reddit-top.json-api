package com.dmytrobilash.domain.repository

import com.dmytrobilash.domain.model.PostModel

interface DataRepository {
    suspend fun getAllTopPost(): List<PostModel>
    suspend fun getTopPostPagination(limit: Int, after: String): List<PostModel>
}