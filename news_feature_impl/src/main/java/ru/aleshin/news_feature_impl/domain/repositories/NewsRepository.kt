package ru.aleshin.news_feature_impl.domain.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.aleshin.news_feature_impl.domain.entites.NewsEntity

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal interface NewsRepository {
    fun fetchNews(category: String, language: String, country: String): Flow<PagingData<NewsEntity>>
}