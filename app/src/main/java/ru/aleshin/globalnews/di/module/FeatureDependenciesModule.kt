package ru.aleshin.globalnews.di.module

import dagger.Binds
import dagger.Module
import ru.aleshin.globalnews.di.component.AppComponent
import ru.aleshin.news_details_feature_impl.di.NewsDetailsFeatureDependencies
import ru.aleshin.news_feature_impl.di.NewsFeatureDependencies
import ru.aleshin.news_settings_feature_impl.di.NewsSettingsFeatureDependencies

/**
 * @author Stanislav Aleshin on 28.10.2022.
 */
@Module
interface FeatureDependenciesModule {

    @Binds
    fun provideNewsFeatureDependencies(component: AppComponent): NewsFeatureDependencies

    @Binds
    fun provideNewsDetailsFeatureDependencies(component: AppComponent): NewsDetailsFeatureDependencies

    @Binds
    fun provideNewsSettingsFeatureDependencies(component: AppComponent): NewsSettingsFeatureDependencies
}