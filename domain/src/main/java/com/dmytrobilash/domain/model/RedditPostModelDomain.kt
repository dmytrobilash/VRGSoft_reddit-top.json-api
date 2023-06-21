package com.dmytrobilash.domain.model

data class RedditPostModelDomain(
    val title: String,
    val author: String,
    val comQuantity: Int,
    val thumbnail: String,
    val createdTime: Long,
    val url: String,
    val name: String
)