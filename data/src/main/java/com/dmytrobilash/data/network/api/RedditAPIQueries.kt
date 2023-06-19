package com.dmytrobilash.data.network.api

import com.dmytrobilash.data.network.model.RedditPostModel
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditAPIQueries {
    //get all top posts
    @GET("top.json")
    suspend fun getTopPosts(): List<RedditPostModel>

    //get all top post for pagination
    @GET("top.json")
    suspend fun getTopPostForPagination(
        @Query("limit") limit: Int,
        @Query("after") after: String): List<RedditPostModel>
}