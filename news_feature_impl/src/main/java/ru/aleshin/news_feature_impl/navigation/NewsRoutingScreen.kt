package ru.aleshin.news_feature_impl.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.aleshin.news_feature_impl.presentaiton.ui.news.NewsFragment

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal object NewsRoutingScreen {

    fun fetchNewsScreen() = FragmentScreen(NavigationKeys.NEWS_FRAGMENT) {
        NewsFragment()
    }
}

internal object NavigationKeys {
    const val NEWS_FRAGMENT = "NewsFragment"
}