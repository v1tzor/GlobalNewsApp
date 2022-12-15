package ru.aleshin.core_db.details

import ru.aleshin.core.common.Mapper

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
data class NewsDetailsModel(
    val content: String,
    val source: String,
    val sourceUrl: String,
    val imageUrl: String,
    val publishedAt: String
) {
    fun <T> map(mapper: Mapper<NewsDetailsModel, T>) = mapper.map(this)
}