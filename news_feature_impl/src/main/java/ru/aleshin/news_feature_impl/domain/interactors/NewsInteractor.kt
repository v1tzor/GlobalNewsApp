package ru.aleshin.news_feature_impl.domain.interactors

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.aleshin.core.functional.Either
import ru.aleshin.news_feature_impl.domain.common.NewsEitherWrapper
import ru.aleshin.news_feature_impl.domain.common.SettingsEitherToEntityMapper
import ru.aleshin.news_feature_impl.domain.entites.NewsEntity
import ru.aleshin.news_feature_impl.domain.entites.NewsFailures
import ru.aleshin.news_feature_impl.domain.repositories.NewsRepository
import ru.aleshin.news_feature_impl.domain.repositories.SettingsRepository
import ru.aleshin.news_settings_feature_api.domain.Categories
import ru.aleshin.news_settings_feature_api.domain.SettingsEntity
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal interface NewsInteractor {

    suspend fun fetchPagingNews(category: Categories?): Flow<PagingData<NewsEntity>>

    suspend fun fetchSettings(): Either<NewsFailures, SettingsEntity>

    class Base @Inject constructor(
        private val newsRepository: NewsRepository,
        private val settingsRepository: SettingsRepository,
        private val eitherWrapper: NewsEitherWrapper,
        private val settingsMapper: SettingsEitherToEntityMapper
    ) : NewsInteractor {

        override suspend fun fetchPagingNews(category: Categories?): Flow<PagingData<NewsEntity>> {
            val correctSettings = settingsMapper.map(fetchSettings(), category)
            return newsRepository.fetchNews(
                correctSettings.defualtCategory.data,
                correctSettings.language.data,
                correctSettings.country.data
            )
        }

        override suspend fun fetchSettings() = eitherWrapper.wrap {
            settingsRepository.fetchSettings()
        }
    }
}
