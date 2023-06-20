package com.dmytrobilash.vrgsofttechtask.model

import com.dmytrobilash.domain.model.PostModel

object MapperDomainToUI {
    fun toPostUIModel(list: List<PostModel>): List<RedditPostUIModel> =
        list.map {
            RedditPostUIModel(
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