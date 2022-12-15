package ru.aleshin.news_favorites_feature_impl.di.holder

import ru.aleshin.module_injector.ComponentHolder
import ru.aleshin.news_favorites_feature_api.NewsFavoritesFeatureApi
import ru.aleshin.news_favorites_feature_impl.di.NewsFavoritesFeatureDependencies
import ru.aleshin.news_favorites_feature_impl.di.component.NewsFavoritesComponent

/**
 * @author Stanislav Aleshin on 28.10.2022.
 */

object NewsFavoritesComponentHolder :
    ComponentHolder<NewsFavoritesFeatureApi, NewsFavoritesFeatureDependencies> {

    private var newsFavoritesComponent: NewsFavoritesComponent? = null

    override fun initComponent(dependencies: NewsFavoritesFeatureDependencies) {
        if (newsFavoritesComponent == null) {
            newsFavoritesComponent = NewsFavoritesComponent.create(dependencies)
        }
    }

    internal fun fetchComponent(): NewsFavoritesComponent {
        return checkNotNull(newsFavoritesComponent) { "NewsFavoritesComponent is not initializing" }
    }

    override fun fetchApi(): NewsFavoritesFeatureApi {
        return fetchComponent()
    }

    override fun reset() {
        newsFavoritesComponent = null
    }
}