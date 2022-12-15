package ru.aleshin.news_feature_impl.di

import android.content.Context
import ru.aleshin.core.navigations.BottomNavRouter
import ru.aleshin.core_db.details.NewsDetailsLocalDataSource
import ru.aleshin.core_db.settings.SettingsLocalDataSource
import ru.aleshin.module_injector.BaseFeatureDependencies
import ru.aleshin.news_details_feature_api.NewsDetailsFeatureStarter

/**
 * @author Stanislav Aleshin on 07.10.2022.
 */
interface NewsFeatureDependencies : BaseFeatureDependencies {
    val applicationContext: Context
    val navRouter: BottomNavRouter
    val detailsFeatureStarter: NewsDetailsFeatureStarter
    val settingsLocalDataSource: SettingsLocalDataSource
    val detailsLocalDataSource: NewsDetailsLocalDataSource
}