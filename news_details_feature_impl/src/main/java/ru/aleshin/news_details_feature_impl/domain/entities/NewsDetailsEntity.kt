package ru.aleshin.news_details_feature_impl.domain.entities

import ru.aleshin.core.common.Mapper

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
data class NewsDetailsEntity(
    val content: String,
    val source: String,
    val sourceUrl: String,
    val imageUrl: String,
    val publishedAt: String
) {
    fun <T> map(mapper: Mapper<NewsDetailsEntity, T>) = mapper.map(this)
}