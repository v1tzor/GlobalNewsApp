package ru.aleshin.news_feature_impl.data.models

/**
 * @author Stanislav Aleshin on 09.10.2022.
 */
internal data class NewsModel(
    val title: String? = null,
    val description: String? = null,
    val content: String? = null,
    val sourceUrl: String? = null,
    val image: String? = null,
    val publishedAt: String? = null,
    val sourceName: String? = null,
    val baseSourceUrl: String? = null
)