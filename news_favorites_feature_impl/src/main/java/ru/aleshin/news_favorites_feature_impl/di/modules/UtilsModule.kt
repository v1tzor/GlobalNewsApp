package ru.aleshin.news_favorites_feature_impl.di.modules

import dagger.Binds
import dagger.Module
import ru.aleshin.core.managers.CoroutineManager
import ru.aleshin.news_favorites_feature_api.NewsFavoritesFeatureStarter
import ru.aleshin.news_favorites_feature_impl.navigation.NewsFavoritesFeatureStarterImpl

/**
 * @author Stanislav Aleshin on 28.10.2022.
 */

@Module
internal interface UtilsModule {

    @Binds
    fun provideFeatureStarter(starter: NewsFavoritesFeatureStarterImpl): NewsFavoritesFeatureStarter

    @Binds
    fun provideCoroutineManager(manager: CoroutineManager.Base): CoroutineManager
}