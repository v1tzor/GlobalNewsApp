package ru.aleshin.news_feature_impl.domain.repositories

import ru.aleshin.news_feature_impl.domain.entites.NewsEntity

/**
 * @author Stanislav Aleshin on 15.10.2022.
 */
internal interface DetailsRepository {
    suspend fun setNews(news: NewsEntity)
}