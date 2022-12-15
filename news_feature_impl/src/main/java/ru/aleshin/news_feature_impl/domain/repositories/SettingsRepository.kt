package ru.aleshin.news_feature_impl.domain.repositories

import ru.aleshin.news_settings_feature_api.domain.SettingsEntity

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal interface SettingsRepository {
    suspend fun fetchSettings(): SettingsEntity
}