package com.dmytrobilash.vrgsofttechtask.mapper

import com.dmytrobilash.domain.model.RedditPostModelDomain
import com.dmytrobilash.vrgsofttechtask.model.RedditPostUIModel

object MapperDomainToUI {
    fun toPostUIModel(list: List<RedditPostModelDomain>): List<RedditPostUIModel> =
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