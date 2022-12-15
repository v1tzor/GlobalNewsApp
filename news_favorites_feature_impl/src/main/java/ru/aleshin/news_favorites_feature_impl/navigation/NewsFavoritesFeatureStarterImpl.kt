package ru.aleshin.news_favorites_feature_impl.navigation

import com.github.terrakok.cicerone.Router
import ru.aleshin.news_favorites_feature_api.NewsFavoritesFeatureStarter
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 28.10.2022.
 */

internal class NewsFavoritesFeatureStarterImpl @Inject constructor(
    private val globalRouter: Router
) : NewsFavoritesFeatureStarter {

    override fun show() {
        globalRouter.navigateTo(NewsFavoritesRoutingScreen.fetchFavoritesScreen())
    }
}