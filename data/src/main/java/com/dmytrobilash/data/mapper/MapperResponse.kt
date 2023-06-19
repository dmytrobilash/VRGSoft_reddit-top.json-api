package com.dmytrobilash.data.mapper

import com.dmytrobilash.data.network.model.RedditPostModel
import com.dmytrobilash.domain.model.PostModel

object MapperResponse {
    fun toPostModel(list: List<RedditPostModel>): List<PostModel> =
        list.map {
            PostModel(
                it.name,
                it.author,
                it.comQuantity,
                it.thumbnail,
                it.createdTime,
                it.url,
                it.title
            )
        }
}