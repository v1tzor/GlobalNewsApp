package ru.aleshin.news_settings_feature_impl.domain.interactor

import ru.aleshin.core.functional.Either
import ru.aleshin.news_settings_feature_api.domain.Categories
import ru.aleshin.news_settings_feature_api.domain.Countries
import ru.aleshin.news_settings_feature_api.domain.Languages
import ru.aleshin.news_settings_feature_api.domain.SettingsEntity
import ru.aleshin.news_settings_feature_impl.domain.common.SettingsEitherWrapper
import ru.aleshin.news_settings_feature_impl.domain.common.SettingsFailures
import ru.aleshin.news_settings_feature_impl.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 24.10.2022.
 */
internal interface SettingsInteractor {

    suspend fun fetchSettings(): Either<SettingsFailures, SettingsEntity>

    suspend fun updateSettings(
        category: Categories?,
        country: Countries?,
        language: Languages?
    ): Either<SettingsFailures, SettingsEntity>

    class Base @Inject constructor(
        private val repository: SettingsRepository,
        private val eitherWrapper: SettingsEitherWrapper
    ) : SettingsInteractor {

        override suspend fun fetchSettings() = eitherWrapper.wrap {
            repository.fetchSettings()
        }

        override suspend fun updateSettings(
            category: Categories?,
            country: Countries?,
            language: Languages?
        ) = eitherWrapper.wrap {
            repository.fetchSettings().let { currentSettings ->
                val newsSettings = SettingsEntity(
                    language = language ?: currentSettings.language,
                    country = country ?: currentSettings.country,
                    defualtCategory = category ?: currentSettings.defualtCategory
                )
                repository.updateSettings(newsSettings)
                return@wrap newsSettings
            }
        }
    }
}