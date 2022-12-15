package ru.aleshin.news_details_feature_impl.data.repository

import ru.aleshin.core_db.details.NewsDetailsLocalDataSource
import ru.aleshin.news_details_feature_impl.data.mapper.NewsDetailsDataToDomainMapper
import ru.aleshin.news_details_feature_impl.domain.entities.NewsDetailsEntity
import ru.aleshin.news_details_feature_impl.domain.repository.DetailsRepository
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
internal class DetailsRepositoryImpl @Inject constructor(
    private val localDataSource: NewsDetailsLocalDataSource,
    private val mapperToDomain: NewsDetailsDataToDomainMapper
) : DetailsRepository {

    override suspend fun fetchNews(): NewsDetailsEntity {
        return localDataSource.read().map(mapperToDomain)
    }
}