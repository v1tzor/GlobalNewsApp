package ru.aleshin.news_favorites_feature_api

import ru.aleshin.module_injector.BaseFeatureApi

/**
 * @author Stanislav Aleshin on 28.10.2022.
 */

interface NewsFavoritesFeatureApi : BaseFeatureApi {
    fun fetchFeatureStarter(): NewsFavoritesFeatureStarter
}