package ru.aleshin.news_settings_feature_impl.di

import android.content.Context
import ru.aleshin.core.navigations.BottomNavRouter
import ru.aleshin.core_db.settings.SettingsLocalDataSource
import ru.aleshin.module_injector.BaseFeatureDependencies

/**
 * @author Stanislav Aleshin on 24.10.2022.
 */
interface NewsSettingsFeatureDependencies : BaseFeatureDependencies {
    val applicationContext: Context
    val navRouter: BottomNavRouter
    val settingsLocalDataSource: SettingsLocalDataSource
}