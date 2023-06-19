package com.dmytrobilash.data.network.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RedditRetrofitBuilder {

    private val BASE_URL = "https://www.reddit.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val redditService: RedditAPIQueries = retrofit.create(RedditAPIQueries::class.java)
}
