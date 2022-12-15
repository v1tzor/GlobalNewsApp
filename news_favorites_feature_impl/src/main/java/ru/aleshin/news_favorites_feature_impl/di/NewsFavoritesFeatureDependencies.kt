package ru.aleshin.news_favorites_feature_impl.di

import com.github.terrakok.cicerone.Router
import ru.aleshin.module_injector.BaseFeatureDependencies

/**
 * @author Stanislav Aleshin on 28.10.2022.
 */

interface NewsFavoritesFeatureDependencies : BaseFeatureDependencies {
    val globalRouter: Router
}