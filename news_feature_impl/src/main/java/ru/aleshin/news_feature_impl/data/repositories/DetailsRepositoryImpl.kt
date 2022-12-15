package ru.aleshin.news_feature_impl.data.repositories

import ru.aleshin.core_db.details.NewsDetailsLocalDataSource
import ru.aleshin.news_feature_impl.data.mappers.NewsDetailsDomainToDataMapper
import ru.aleshin.news_feature_impl.domain.entites.NewsEntity
import ru.aleshin.news_feature_impl.domain.repositories.DetailsRepository
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 15.10.2022.
 */
internal class DetailsRepositoryImpl @Inject constructor(
    private val localDataSource: NewsDetailsLocalDataSource,
    private val mapperToData: NewsDetailsDomainToDataMapper
) : DetailsRepository {

    override suspend fun setNews(news: NewsEntity) {
        localDataSource.save(news.map(mapperToData))
    }
}
