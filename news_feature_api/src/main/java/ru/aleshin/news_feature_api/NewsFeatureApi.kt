package ru.aleshin.news_feature_api

import ru.aleshin.module_injector.BaseFeatureApi

/**
 * @author Stanislav Aleshin on 07.10.2022.
 */
interface NewsFeatureApi : BaseFeatureApi {
    fun fetchFeatureStarter(): NewsFeatureStarter
}