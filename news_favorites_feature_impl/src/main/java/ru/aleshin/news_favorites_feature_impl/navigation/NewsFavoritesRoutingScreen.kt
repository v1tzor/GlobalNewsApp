package ru.aleshin.news_favorites_feature_impl.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.aleshin.news_favorites_feature_impl.presentation.ui.FavoritesFragment

/**
 * @author Stanislav Aleshin on 28.10.2022.
 */

internal object NewsFavoritesRoutingScreen {
    fun fetchFavoritesScreen() = FragmentScreen { FavoritesFragment() }
}