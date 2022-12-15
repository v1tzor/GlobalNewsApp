package ru.aleshin.news_feature_impl.di.component

import dagger.Component
import ru.aleshin.core.di.FeatureScope
import ru.aleshin.news_feature_api.NewsFeatureApi
import ru.aleshin.news_feature_impl.di.NewsFeatureDependencies
import ru.aleshin.news_feature_impl.di.module.*
import ru.aleshin.news_feature_impl.presentaiton.ui.news.NewsFragment

/**
 * @author Stanislav Aleshin on 07.10.2022.
 */
@FeatureScope
@Component(
    dependencies = [NewsFeatureDependencies::class],
    modules = [
        UtilsModule::class,
        NetworkModule::class,
        DataModule::class,
        DomainModule::class,
        PresentationModule::class,
        ViewModelModule::class
    ]
)
internal interface NewsComponent : NewsFeatureApi {

    fun inject(fragment: NewsFragment)

    @Component.Builder
    interface Builder {
        fun featureDependencies(dependencies: NewsFeatureDependencies): Builder
        fun networkModule(module: NetworkModule): Builder
        fun utilsModule(module: UtilsModule): Builder
        fun build(): NewsComponent
    }

    companion object {
        fun create(dependencies: NewsFeatureDependencies): NewsComponent {
            return DaggerNewsComponent.builder()
                .featureDependencies(dependencies)
                .networkModule(NetworkModule())
                .utilsModule(UtilsModule())
                .build()
        }
    }
}