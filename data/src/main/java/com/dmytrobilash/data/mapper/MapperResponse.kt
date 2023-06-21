package com.dmytrobilash.data.mapper

import com.dmytrobilash.data.network.model.RedditPostModel
import com.dmytrobilash.domain.model.RedditPostModelDomain

object MapperResponse {

    fun toPostModel(list: List<RedditPostModel>): List<RedditPostModelDomain> =
        list.map {
            RedditPostModelDomain(
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