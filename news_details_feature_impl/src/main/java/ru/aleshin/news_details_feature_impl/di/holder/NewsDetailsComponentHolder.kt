package ru.aleshin.news_details_feature_impl.di.holder

import ru.aleshin.module_injector.ComponentHolder
import ru.aleshin.news_details_feature_api.NewsDetailsFeatureApi
import ru.aleshin.news_details_feature_impl.di.NewsDetailsFeatureDependencies
import ru.aleshin.news_details_feature_impl.di.component.NewsDetailsComponent

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
object NewsDetailsComponentHolder :
    ComponentHolder<NewsDetailsFeatureApi, NewsDetailsFeatureDependencies> {

    private var newsDetailsComponent: NewsDetailsComponent? = null

    override fun initComponent(dependencies: NewsDetailsFeatureDependencies) {
        if (newsDetailsComponent == null) {
            newsDetailsComponent = NewsDetailsComponent.create(dependencies)
        }
    }

    internal fun fetchComponent(): NewsDetailsComponent {
        return checkNotNull(newsDetailsComponent) { "NewsDetailsComponent is not initializing" }
    }

    override fun fetchApi(): NewsDetailsFeatureApi {
        return fetchComponent()
    }

    override fun reset() {
        newsDetailsComponent = null
    }
}