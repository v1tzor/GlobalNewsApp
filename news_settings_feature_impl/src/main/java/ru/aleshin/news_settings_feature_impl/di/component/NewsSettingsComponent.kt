package ru.aleshin.news_settings_feature_impl.di.component

import dagger.Component
import ru.aleshin.core.di.FeatureScope
import ru.aleshin.news_settings_feature_api.di.NewsSettingsFeatureApi
import ru.aleshin.news_settings_feature_api.di.NewsSettingsFeatureStarter
import ru.aleshin.news_settings_feature_impl.di.NewsSettingsFeatureDependencies
import ru.aleshin.news_settings_feature_impl.di.modules.DataModule
import ru.aleshin.news_settings_feature_impl.di.modules.DomainModule
import ru.aleshin.news_settings_feature_impl.di.modules.PresentationModule
import ru.aleshin.news_settings_feature_impl.di.modules.UtilsModule
import ru.aleshin.news_settings_feature_impl.di.modules.ViewModelModule
import ru.aleshin.news_settings_feature_impl.presentation.ui.categories.CategoriesDialogFragment
import ru.aleshin.news_settings_feature_impl.presentation.ui.countries.CountriesDialogFragment
import ru.aleshin.news_settings_feature_impl.presentation.ui.languages.LanguagesDialogFragment
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.SettingsFragment

/**
 * @author Stanislav Aleshin on 24.10.2022.
 */
@FeatureScope
@Component(
    dependencies = [NewsSettingsFeatureDependencies::class],
    modules = [
        UtilsModule::class,
        DataModule::class,
        DomainModule::class,
        PresentationModule::class,
        ViewModelModule::class
    ]
)
internal interface NewsSettingsComponent : NewsSettingsFeatureApi {

    override fun fetchFeatureStarter(): NewsSettingsFeatureStarter

    fun inject(fragment: SettingsFragment)

    fun inject(dialogFragment: CategoriesDialogFragment)

    fun inject(dialogFragment: LanguagesDialogFragment)

    fun inject(dialogFragment: CountriesDialogFragment)

    @Component.Builder
    interface Builder {
        fun featureDependencies(dependencies: NewsSettingsFeatureDependencies): Builder
        fun utilsModule(module: UtilsModule): Builder
        fun build(): NewsSettingsComponent
    }

    companion object {
        fun create(dependencies: NewsSettingsFeatureDependencies): NewsSettingsComponent {
            return DaggerNewsSettingsComponent.builder()
                .featureDependencies(dependencies)
                .utilsModule(UtilsModule())
                .build()
        }
    }
}