package ru.aleshin.news_settings_feature_impl.di.modules

import dagger.Binds
import dagger.Module
import ru.aleshin.news_settings_feature_impl.presentation.mappers.SettingsDomainToUiMapper
import ru.aleshin.news_settings_feature_impl.presentation.mappers.SettingsUiToDomainMapper

/**
 * @author Stanislav Aleshin on 27.10.2022.
 */
@Module
internal interface PresentationModule {

    @Binds
    fun provideSettingsUiToDomainMapper(mapper: SettingsUiToDomainMapper.Base): SettingsUiToDomainMapper

    @Binds
    fun provideSettingsDomainToUiMapper(mapper: SettingsDomainToUiMapper.Base): SettingsDomainToUiMapper
}