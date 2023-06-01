package com.dmytrobilash.vrgsofttechtask.model.network

import com.dmytrobilash.vrgsofttechtask.model.domain.RedditResponse

import retrofit2.http.GET
import retrofit2.http.Query

interface RedditInterfaceAPI {

    //get all top posts
    @GET("top.json")
    suspend fun getTopPosts(): RedditResponse

    //get all top post for pagination
    @GET("top.json")
    suspend fun getTopPostForPagination(
        @Query("limit") limit: Int,
        @Query("after") after: String): RedditResponse
}