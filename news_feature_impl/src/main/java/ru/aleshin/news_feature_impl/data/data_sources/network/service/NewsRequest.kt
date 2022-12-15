package ru.aleshin.news_feature_impl.data.data_sources.network.service

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal data class NewsRequest(
    val category: String,
    val language: String,
    val country: String
)
