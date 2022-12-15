package ru.aleshin.news_settings_feature_impl.di.holder

import ru.aleshin.module_injector.ComponentHolder
import ru.aleshin.news_settings_feature_api.di.NewsSettingsFeatureApi
import ru.aleshin.news_settings_feature_impl.di.NewsSettingsFeatureDependencies
import ru.aleshin.news_settings_feature_impl.di.component.NewsSettingsComponent

/**
 * @author Stanislav Aleshin on 24.10.2022.
 */
object NewsSettingsComponentHolder :
    ComponentHolder<NewsSettingsFeatureApi, NewsSettingsFeatureDependencies> {

    private var newsSettingsComponent: NewsSettingsComponent? = null

    override fun initComponent(dependencies: NewsSettingsFeatureDependencies) {
        if (newsSettingsComponent == null) {
            newsSettingsComponent = NewsSettingsComponent.create(dependencies)
        }
    }

    internal fun fetchComponent(): NewsSettingsComponent {
        return checkNotNull(newsSettingsComponent) { "NewsSettingsComponent is not initializing " }
    }

    override fun fetchApi(): NewsSettingsFeatureApi {
        return fetchComponent()
    }

    override fun reset() {
        newsSettingsComponent = null
    }
}