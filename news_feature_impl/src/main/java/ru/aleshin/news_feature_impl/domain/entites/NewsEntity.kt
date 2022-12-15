package ru.aleshin.news_feature_impl.domain.entites

import ru.aleshin.core.common.Mapper

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal data class NewsEntity(
    val title: String,
    val content: String,
    val source: String,
    val sourceUrl: String,
    val image: String,
    val publishedAt: String
) {
    fun <T> map(mapper: Mapper<NewsEntity, T>) = mapper.map(this)
}
