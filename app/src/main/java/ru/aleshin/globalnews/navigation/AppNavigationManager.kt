package ru.aleshin.globalnews.navigation

import ru.aleshin.core.navigations.GlobalRouter
import ru.aleshin.news_feature_api.NewsFeatureStarter
import ru.aleshin.news_settings_feature_api.di.NewsSettingsFeatureStarter
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author Stanislav Aleshin on 28.10.2022.
 */
interface AppNavigationManager {

    fun showNavFragment()

    fun selectNewsFeature()

    fun selectSettingsFeature()

    class Base @Inject constructor(
        private val newsFeatureStarter: Provider<NewsFeatureStarter>,
        private val settingsFeatureStarter: Provider<NewsSettingsFeatureStarter>,
        private val globalRouter: GlobalRouter
    ) : AppNavigationManager {

        override fun showNavFragment() {
            globalRouter.newRootScreen(GlobalRoutingScreen.fetchNavScreen())
        }

        override fun selectNewsFeature() {
            newsFeatureStarter.get().showFeature()
        }

        override fun selectSettingsFeature() {
            settingsFeatureStarter.get().showFeature()
        }
    }
}