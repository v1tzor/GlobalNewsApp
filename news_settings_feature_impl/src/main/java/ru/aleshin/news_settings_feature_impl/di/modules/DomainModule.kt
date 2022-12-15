package ru.aleshin.news_settings_feature_impl.di.modules

import dagger.Binds
import dagger.Module
import ru.aleshin.core.di.FeatureScope
import ru.aleshin.news_settings_feature_impl.domain.common.SettingsEitherWrapper
import ru.aleshin.news_settings_feature_impl.domain.common.SettingsErrorHandler
import ru.aleshin.news_settings_feature_impl.domain.interactor.SettingsInteractor

/**
 * @author Stanislav Aleshin on 27.10.2022.
 */
@Module
internal interface DomainModule {

    @Binds
    @FeatureScope
    fun provideSettingsInteractor(interactor: SettingsInteractor.Base): SettingsInteractor

    @Binds
    fun provideSettingsEitherWrapper(wrapper: SettingsEitherWrapper.Base): SettingsEitherWrapper

    @Binds
    fun provideSettingsErrorHandler(handler: SettingsErrorHandler.Base): SettingsErrorHandler
}