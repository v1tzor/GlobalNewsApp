package ru.aleshin.news_feature_impl.domain.interactors

import ru.aleshin.core.functional.EitherLeft
import ru.aleshin.news_feature_impl.domain.common.NewsEitherWrapper
import ru.aleshin.news_feature_impl.domain.entites.NewsEntity
import ru.aleshin.news_feature_impl.domain.entites.NewsFailures
import ru.aleshin.news_feature_impl.domain.repositories.DetailsRepository
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 15.10.2022.
 */
internal interface DetailsInteractor {

    suspend fun saveNews(news: NewsEntity): EitherLeft<NewsFailures>

    class Base @Inject constructor(
        private val detailsRepository: DetailsRepository,
        private val eitherWrapper: NewsEitherWrapper
    ) : DetailsInteractor {

        override suspend fun saveNews(news: NewsEntity) = eitherWrapper.wrap {
            detailsRepository.setNews(news)
        }
    }
}