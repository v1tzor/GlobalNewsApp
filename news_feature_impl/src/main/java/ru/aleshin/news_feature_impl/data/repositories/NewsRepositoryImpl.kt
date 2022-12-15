package ru.aleshin.news_feature_impl.data.repositories

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.aleshin.news_feature_impl.data.data_sources.network.NewsPagingDataSource
import ru.aleshin.news_feature_impl.data.data_sources.network.service.NewsRequest
import ru.aleshin.news_feature_impl.data.mappers.NewsDataToDomainMapper
import ru.aleshin.news_feature_impl.domain.entites.NewsEntity
import ru.aleshin.news_feature_impl.domain.repositories.NewsRepository
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 09.10.2022.
 */
internal class NewsRepositoryImpl @Inject constructor(
    private val pagingDataSource: NewsPagingDataSource,
    private val mapperToDomain: NewsDataToDomainMapper
) : NewsRepository {

    override fun fetchNews(
        category: String,
        language: String,
        country: String
    ): Flow<PagingData<NewsEntity>> {
        val newsRequest = NewsRequest(category, language, country)

        return pagingDataSource.fetchNews(newsRequest).map { pagingData ->
            pagingData.map { mapperToDomain.map(it) }
        }
    }
}