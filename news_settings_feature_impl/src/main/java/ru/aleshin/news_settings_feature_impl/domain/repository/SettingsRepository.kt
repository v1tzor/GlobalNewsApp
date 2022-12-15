package ru.aleshin.news_settings_feature_impl.domain.repository

import ru.aleshin.news_settings_feature_api.domain.SettingsEntity

/**
 * @author Stanislav Aleshin on 24.10.2022.
 */
internal interface SettingsRepository {

    suspend fun fetchSettings(): SettingsEntity

    suspend fun updateSettings(settings: SettingsEntity)
}