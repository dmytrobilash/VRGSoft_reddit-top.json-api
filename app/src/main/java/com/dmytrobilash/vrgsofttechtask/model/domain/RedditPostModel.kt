package com.dmytrobilash.vrgsofttechtask.model.domain

import com.google.gson.annotations.SerializedName

//model
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
//response from API
data class RedditResponse(
    val kind: String,
    val data: RedditData
)

//sections of Reddit response
data class RedditData(
    val children: List<RedditChild>
)

//child items in data section
data class RedditChild(
    val kind: String,
    val data: RedditPostModel
)

//transformation
fun RedditPostModel.asModel(): RedditPostModel {
    return RedditPostModel(
        title = title,
        author = author,
        comQuantity = comQuantity,
        thumbnail = thumbnail,
        createdTime = createdTime,
        url = url,
        name = name
    )
}

