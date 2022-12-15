package ru.aleshin.news_feature_impl.data.data_sources.network

import ru.aleshin.core.functional.ResponseResult
import ru.aleshin.core.handlers.ResponseHandler
import ru.aleshin.news_feature_impl.data.data_sources.network.service.NewsRequest
import ru.aleshin.news_feature_impl.data.data_sources.network.service.NewsService
import ru.aleshin.news_feature_impl.data.mappers.NewsResponseToModelMapper
import ru.aleshin.news_feature_impl.data.models.NewsModel
import ru.aleshin.news_feature_impl.data.models.NewsResponse
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 22.10.2022.
 */
internal interface NewsRemoteDataSource {

    suspend fun fetchNews(newsRequest: NewsRequest, page: Int): ResponseResult<List<NewsModel>>

    class Base @Inject constructor(
        private val service: NewsService,
        private val responseHandler: ResponseHandler<NewsResponse>,
        private val mapperToData: NewsResponseToModelMapper
    ) : NewsRemoteDataSource {

        override suspend fun fetchNews(
            newsRequest: NewsRequest,
            page: Int
        ) = responseHandler.handle {
            service.fetchNews(
                category = newsRequest.category,
                language = newsRequest.language,
                country = newsRequest.country,
                page = page
            )
        }.map { mapperToData.map(it) }
    }
}