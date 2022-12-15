package ru.aleshin.news_settings_feature_impl.navigation

import ru.aleshin.core.navigations.BottomNavRouter
import ru.aleshin.news_settings_feature_api.di.NewsSettingsFeatureStarter
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 24.10.2022.
 */
internal class SettingsFeatureStarterImpl @Inject constructor(
    private val navRouter: BottomNavRouter
) : NewsSettingsFeatureStarter {

    override fun showFeature() {
        navRouter.selectBottomTabScreen(SettingsRoutingScreens.fetchSettingsScreen())
    }
}