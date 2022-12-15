package ru.aleshin.news_details_feature_impl.di.modules

import dagger.Binds
import dagger.Module
import ru.aleshin.core.managers.CoroutineManager
import ru.aleshin.news_details_feature_api.NewsDetailsFeatureStarter
import ru.aleshin.news_details_feature_impl.navigations.NavigationManager
import ru.aleshin.news_details_feature_impl.navigations.NewsDetailsFeatureStarterImpl

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
@Module
internal interface UtilsModule {

    @Binds
    fun provideDetailsFeatureStarter(starter: NewsDetailsFeatureStarterImpl): NewsDetailsFeatureStarter

    @Binds
    fun provideCoroutineManager(manager: CoroutineManager.Base): CoroutineManager

    @Binds
    fun provideNavigationManager(manager: NavigationManager.Base): NavigationManager
}