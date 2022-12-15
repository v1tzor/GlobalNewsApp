package ru.aleshin.news_settings_feature_impl.data.repository

import ru.aleshin.core_db.settings.SettingsLocalDataSource
import ru.aleshin.news_settings_feature_api.data.SettingsDataToDomainMapper
import ru.aleshin.news_settings_feature_api.data.SettingsDomainToDataMapper
import ru.aleshin.news_settings_feature_api.domain.SettingsEntity
import ru.aleshin.news_settings_feature_impl.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 24.10.2022.
 */
internal class SettingsRepositoryImpl @Inject constructor(
    private val localDataSource: SettingsLocalDataSource,
    private val mapperToDomain: SettingsDataToDomainMapper,
    private val mapperToData: SettingsDomainToDataMapper
) : SettingsRepository {

    override suspend fun fetchSettings(): SettingsEntity {
        return localDataSource.fetchSettings().map(mapperToDomain)
    }

    override suspend fun updateSettings(settings: SettingsEntity) {
        return localDataSource.updateSettings(settings.map(mapperToData))
    }
}