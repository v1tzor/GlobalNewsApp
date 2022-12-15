package ru.aleshin.news_feature_impl.data.data_sources.network.paging

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal data class PageInfo(
    val current: Int,
    val next: Int?,
    val prev: Int?,
    val size: Int
)