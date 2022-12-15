package ru.aleshin.news_feature_impl.di.holder

import ru.aleshin.module_injector.ComponentHolder
import ru.aleshin.news_feature_api.NewsFeatureApi
import ru.aleshin.news_feature_impl.di.NewsFeatureDependencies
import ru.aleshin.news_feature_impl.di.component.NewsComponent

/**
 * @author Stanislav Aleshin on 07.10.2022.
 */
object NewsComponentHolder : ComponentHolder<NewsFeatureApi, NewsFeatureDependencies> {

    private var newsComponent: NewsComponent? = null

    override fun initComponent(dependencies: NewsFeatureDependencies) {
        if (newsComponent == null) {
            newsComponent = NewsComponent.create(dependencies)
        }
    }

    internal fun fetchComponent(): NewsComponent {
        return checkNotNull(newsComponent) { "News component is not initializing" }
    }

    override fun fetchApi(): NewsFeatureApi {
        return fetchComponent()
    }

    override fun reset() {
        newsComponent = null
    }
}