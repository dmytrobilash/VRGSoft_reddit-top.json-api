package com.dmytrobilash.data.network.api

import com.dmytrobilash.data.network.model.RedditResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditAPIQueries {
    //get all top posts
    @GET("top.json")
    suspend fun getTopPosts(): RedditResponse

    //get all top post for pagination
    @GET("top.json")
    suspend fun getTopPostForPagination(
        @Query("limit") limit: Int,
        @Query("after") after: String): RedditResponse
}