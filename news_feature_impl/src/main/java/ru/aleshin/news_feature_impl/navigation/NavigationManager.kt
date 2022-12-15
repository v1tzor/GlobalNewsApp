package ru.aleshin.news_feature_impl.navigation

import ru.aleshin.news_details_feature_api.NewsDetailsFeatureStarter
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 15.10.2022.
 */
internal interface NavigationManager {

    fun showDetailsScreen()

    class Base @Inject constructor(
        private val detailsFeatureStarter: NewsDetailsFeatureStarter
    ) : NavigationManager {

        override fun showDetailsScreen() {
            detailsFeatureStarter.showFeature()
        }
    }
}