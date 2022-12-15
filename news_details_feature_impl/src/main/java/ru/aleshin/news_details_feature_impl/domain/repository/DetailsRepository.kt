package ru.aleshin.news_details_feature_impl.domain.repository

import ru.aleshin.news_details_feature_impl.domain.entities.NewsDetailsEntity

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
internal interface DetailsRepository {
    suspend fun fetchNews(): NewsDetailsEntity
}