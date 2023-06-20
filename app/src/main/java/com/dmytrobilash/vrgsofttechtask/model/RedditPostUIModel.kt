package com.dmytrobilash.vrgsofttechtask.model

data class RedditPostUIModel(
    val title: String,
    val author: String,
    val comQuantity: Int,
    val thumbnail: String,
    val createdTime: Long,
    val url: String,
    val name: String
)