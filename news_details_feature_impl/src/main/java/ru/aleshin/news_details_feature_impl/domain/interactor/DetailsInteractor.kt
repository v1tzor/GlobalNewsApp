package ru.aleshin.news_details_feature_impl.domain.interactor

import ru.aleshin.core.functional.Either
import ru.aleshin.news_details_feature_impl.domain.common.NewsDetailsEitherWrapper
import ru.aleshin.news_details_feature_impl.domain.entities.DetailsFailures
import ru.aleshin.news_details_feature_impl.domain.entities.NewsDetailsEntity
import ru.aleshin.news_details_feature_impl.domain.repository.DetailsRepository
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
internal interface DetailsInteractor {

    suspend fun fetchNews(): Either<DetailsFailures, NewsDetailsEntity>

    class Base @Inject constructor(
        private val detailsRepository: DetailsRepository,
        private val eitherWrapper: NewsDetailsEitherWrapper
    ) : DetailsInteractor {

        override suspend fun fetchNews() = eitherWrapper.wrap {
            detailsRepository.fetchNews()
        }
    }
}