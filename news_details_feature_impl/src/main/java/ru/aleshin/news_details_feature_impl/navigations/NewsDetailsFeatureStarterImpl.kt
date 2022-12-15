package ru.aleshin.news_details_feature_impl.navigations

import ru.aleshin.core.navigations.GlobalRouter
import ru.aleshin.news_details_feature_api.NewsDetailsFeatureStarter
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
internal class NewsDetailsFeatureStarterImpl @Inject constructor(
    private val globalRouter: GlobalRouter
) : NewsDetailsFeatureStarter {

    override fun showFeature() {
        globalRouter.navigateTo(NewsDetailsRoutingScreen.fetchDetailsScreen())
    }
}