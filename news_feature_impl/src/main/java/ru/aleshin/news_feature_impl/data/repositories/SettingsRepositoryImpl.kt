package ru.aleshin.news_feature_impl.data.repositories

import ru.aleshin.core_db.settings.SettingsLocalDataSource
import ru.aleshin.news_feature_impl.domain.repositories.SettingsRepository
import ru.aleshin.news_settings_feature_api.data.SettingsDataToDomainMapper
import ru.aleshin.news_settings_feature_api.domain.SettingsEntity
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 09.10.2022.
 */
internal class SettingsRepositoryImpl @Inject constructor(
    private val localDataSource: SettingsLocalDataSource,
    private val mapper: SettingsDataToDomainMapper
) : SettingsRepository {

    override suspend fun fetchSettings(): SettingsEntity {
        return localDataSource.fetchSettings().map(mapper)
    }
}