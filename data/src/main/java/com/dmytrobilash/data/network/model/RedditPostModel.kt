package com.dmytrobilash.data.network.model

import com.google.gson.annotations.SerializedName

data class RedditPostModel(
    val title: String,
    val author: String,
    @SerializedName("num_comments")
    val comQuantity: Int,
    val thumbnail: String,
    @SerializedName("created_utc")
    val createdTime: Long,
    val url: String,
    val name: String
)