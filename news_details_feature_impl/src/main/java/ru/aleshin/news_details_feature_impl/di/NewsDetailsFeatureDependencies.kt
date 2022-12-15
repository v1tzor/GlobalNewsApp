package ru.aleshin.news_details_feature_impl.di

import ru.aleshin.core.navigations.GlobalRouter
import ru.aleshin.core_db.details.NewsDetailsLocalDataSource
import ru.aleshin.module_injector.BaseFeatureDependencies

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
interface NewsDetailsFeatureDependencies : BaseFeatureDependencies {
    val globalRouter: GlobalRouter
    val detailsLocalDataSource: NewsDetailsLocalDataSource
}