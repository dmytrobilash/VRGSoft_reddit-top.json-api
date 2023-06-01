package com.dmytrobilash.vrgsofttechtask.model.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RedditImplAPI {

    private val BASE_URL = "https://www.reddit.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val redditService: RedditInterfaceAPI = retrofit.create(RedditInterfaceAPI::class.java)

}