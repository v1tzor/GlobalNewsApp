package ru.aleshin.news_details_feature_api

import ru.aleshin.module_injector.BaseFeatureApi

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
interface NewsDetailsFeatureApi : BaseFeatureApi {
    fun fetchFeatureStarter(): NewsDetailsFeatureStarter
}