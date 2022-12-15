package ru.aleshin.news_favorites_feature_impl.di.component

import dagger.Component
import ru.aleshin.core.di.FeatureScope
import ru.aleshin.news_favorites_feature_api.NewsFavoritesFeatureApi
import ru.aleshin.news_favorites_feature_api.NewsFavoritesFeatureStarter
import ru.aleshin.news_favorites_feature_impl.di.NewsFavoritesFeatureDependencies
import ru.aleshin.news_favorites_feature_impl.di.modules.UtilsModule
import ru.aleshin.news_favorites_feature_impl.di.modules.ViewModelModule
import ru.aleshin.news_favorites_feature_impl.presentation.ui.FavoritesFragment

/**
 * @author Stanislav Aleshin on 28.10.2022.
 */

@FeatureScope
@Component(
    dependencies = [NewsFavoritesFeatureDependencies::class],
    modules = [UtilsModule::class, ViewModelModule::class]
)
internal interface NewsFavoritesComponent : NewsFavoritesFeatureApi {

    override fun fetchFeatureStarter(): NewsFavoritesFeatureStarter

    fun inject(fragment: FavoritesFragment)

    @Component.Builder
    interface Builder {
        fun featureDependencies(dependencies: NewsFavoritesFeatureDependencies): Builder
        fun build(): NewsFavoritesComponent
    }

    companion object {
        fun create(dependencies: NewsFavoritesFeatureDependencies): NewsFavoritesComponent {
            return DaggerNewsFavoritesComponent.builder()
                .featureDependencies(dependencies)
                .build()
        }
    }
}