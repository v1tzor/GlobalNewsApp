package ru.aleshin.news_feature_impl.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.aleshin.core.managers.CoroutineManager
import ru.aleshin.core.managers.DateManager
import ru.aleshin.core.managers.ResourceManager
import ru.aleshin.core.navigations.BottomNavRouter
import ru.aleshin.news_feature_api.NewsFeatureStarter
import ru.aleshin.news_feature_impl.navigation.NewsFeatureStarterImpl
import ru.aleshin.news_settings_feature_api.data.SettingsDataToDomainMapper
import ru.aleshin.news_settings_feature_api.data.SettingsDomainToDataMapper

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
@Module
internal class UtilsModule {

    @Provides
    fun provideNewsFeatureStarter(navRouter: BottomNavRouter): NewsFeatureStarter {
        return NewsFeatureStarterImpl(navRouter)
    }

    @Provides
    fun provideCoroutineManager(): CoroutineManager {
        return CoroutineManager.Base()
    }

    @Provides
    fun provideSettingsDomainToDataMapper(): SettingsDomainToDataMapper {
        return SettingsDomainToDataMapper.Base()
    }

    @Provides
    fun provideSettingsDataToDomainMapper(): SettingsDataToDomainMapper {
        return SettingsDataToDomainMapper.Base()
    }

    @Provides
    fun provideResourceManager(context: Context): ResourceManager {
        return ResourceManager.Base(context)
    }

    @Provides
    fun provideDateManager(): DateManager {
        return DateManager.Base()
    }
}