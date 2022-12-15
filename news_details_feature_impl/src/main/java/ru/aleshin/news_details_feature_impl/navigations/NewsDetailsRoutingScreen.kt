package ru.aleshin.news_details_feature_impl.navigations

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.aleshin.news_details_feature_impl.presentation.ui.details.NewsDetailsFragment

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
internal object NewsDetailsRoutingScreen {

    fun fetchDetailsScreen() = FragmentScreen(NavigationKeys.NEWS_DETAILS_FRAGMENT) {
        NewsDetailsFragment()
    }
}

internal object NavigationKeys {
    const val NEWS_DETAILS_FRAGMENT = "NewsDetailsFragment"
}