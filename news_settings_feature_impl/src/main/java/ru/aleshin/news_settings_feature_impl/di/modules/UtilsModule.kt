package ru.aleshin.news_settings_feature_impl.di.modules

import dagger.Module
import dagger.Provides
import ru.aleshin.core.managers.CoroutineManager
import ru.aleshin.core.navigations.BottomNavRouter
import ru.aleshin.news_settings_feature_api.data.SettingsDataToDomainMapper
import ru.aleshin.news_settings_feature_api.data.SettingsDomainToDataMapper
import ru.aleshin.news_settings_feature_api.di.NewsSettingsFeatureStarter
import ru.aleshin.news_settings_feature_impl.navigation.SettingsFeatureStarterImpl
import ru.aleshin.news_settings_feature_impl.navigation.SettingsNavigationManager

/**
 * @author Stanislav Aleshin on 24.10.2022.
 */
@Module
internal class UtilsModule {

    @Provides
    fun provideCoroutineManager(): CoroutineManager {
        return CoroutineManager.Base()
    }

    @Provides
    fun provideFeatureStarter(router: BottomNavRouter): NewsSettingsFeatureStarter {
        return SettingsFeatureStarterImpl(router)
    }

    @Provides
    fun provideNavigationManager(router: BottomNavRouter): SettingsNavigationManager {
        return SettingsNavigationManager.Base(router)
    }

    @Provides
    fun provideSettingsDomainToDataMapper(): SettingsDomainToDataMapper {
        return SettingsDomainToDataMapper.Base()
    }

    @Provides
    fun provideSettingsDataToDomainMapper(): SettingsDataToDomainMapper {
        return SettingsDataToDomainMapper.Base()
    }
}