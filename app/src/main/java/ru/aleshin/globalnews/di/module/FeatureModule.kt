package ru.aleshin.globalnews.di.module

import dagger.Module
import dagger.Provides
import ru.aleshin.news_details_feature_impl.di.NewsDetailsFeatureDependencies
import ru.aleshin.news_details_feature_impl.di.holder.NewsDetailsComponentHolder
import ru.aleshin.news_feature_impl.di.NewsFeatureDependencies
import ru.aleshin.news_feature_impl.di.holder.NewsComponentHolder
import ru.aleshin.news_settings_feature_impl.di.NewsSettingsFeatureDependencies
import ru.aleshin.news_settings_feature_impl.di.holder.NewsSettingsComponentHolder

/**
 * @author Stanislav Aleshin on 28.10.2022.
 */
@Module
class FeatureModule {

    @Provides
    fun provideNewsFeatureStarter(
        dependencies: NewsFeatureDependencies
    ) = NewsComponentHolder.let {
        it.initComponent(dependencies)
        it.fetchApi().fetchFeatureStarter()
    }

    @Provides
    fun provideNewsSettingsFeatureStarter(
        dependencies: NewsSettingsFeatureDependencies
    ) = NewsSettingsComponentHolder.let {
        it.initComponent(dependencies)
        it.fetchApi().fetchFeatureStarter()
    }

    @Provides
    fun provideNewsDetailsFeatureStarter(
        dependencies: NewsDetailsFeatureDependencies
    ) = NewsDetailsComponentHolder.let {
        it.initComponent(dependencies)
        it.fetchApi().fetchFeatureStarter()
    }
}