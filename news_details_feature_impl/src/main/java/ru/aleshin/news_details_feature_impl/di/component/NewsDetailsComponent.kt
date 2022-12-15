package ru.aleshin.news_details_feature_impl.di.component

import dagger.Component
import ru.aleshin.core.di.FeatureScope
import ru.aleshin.news_details_feature_api.NewsDetailsFeatureApi
import ru.aleshin.news_details_feature_impl.di.NewsDetailsFeatureDependencies
import ru.aleshin.news_details_feature_impl.di.modules.DataModule
import ru.aleshin.news_details_feature_impl.di.modules.DomainModule
import ru.aleshin.news_details_feature_impl.di.modules.UtilsModule
import ru.aleshin.news_details_feature_impl.di.modules.ViewModelModule
import ru.aleshin.news_details_feature_impl.presentation.ui.details.NewsDetailsFragment

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
@FeatureScope
@Component(
    modules = [UtilsModule::class, DataModule::class, DomainModule::class, ViewModelModule::class],
    dependencies = [NewsDetailsFeatureDependencies::class]
)
internal interface NewsDetailsComponent : NewsDetailsFeatureApi {

    fun inject(fragment: NewsDetailsFragment)

    @Component.Builder
    interface Builder {
        fun featureDependencies(dependencies: NewsDetailsFeatureDependencies): Builder
        fun build(): NewsDetailsComponent
    }

    companion object {
        fun create(dependencies: NewsDetailsFeatureDependencies): NewsDetailsComponent {
            return DaggerNewsDetailsComponent.builder()
                .featureDependencies(dependencies)
                .build()
        }
    }
}