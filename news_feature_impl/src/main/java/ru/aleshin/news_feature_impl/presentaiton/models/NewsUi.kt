package ru.aleshin.news_feature_impl.presentaiton.models

import ru.aleshin.core.common.Mapper

/**
 * @author Stanislav Aleshin on 14.10.2022.
 */
internal data class NewsUi(
    val title: String,
    val content: String,
    val source: String,
    val sourceUrl: String,
    val imageUrl: String,
    val publishedAt: String
) {
    fun <T> map(mapper: Mapper<NewsUi, T>) = mapper.map(this)
}
