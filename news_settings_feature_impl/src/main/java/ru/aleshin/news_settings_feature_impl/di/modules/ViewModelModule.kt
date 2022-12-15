package ru.aleshin.news_settings_feature_impl.di.modules

import dagger.Binds
import dagger.Module
import ru.aleshin.core.di.FeatureScope
import ru.aleshin.core.platform.viewmodel.BaseViewModel
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.SettingsViewModel
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.common.SettingsRequestHandler
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.communications.SettingsCommunications
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.communications.SettingsCommunicator
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.communications.SettingsStateCommunicator

/**
 * @author Stanislav Aleshin on 27.10.2022.
 */

@Module
internal interface ViewModelModule {

    @Binds
    fun provideSettingsViewModel(viewModel: SettingsViewModel): BaseViewModel

    @Binds
    fun provideSettingsViewModelFactory(factory: SettingsViewModel.Factory): BaseViewModel.BaseViewModelFactory

    @Binds
    fun provideSettingsRequestHandler(handler: SettingsRequestHandler.Base): SettingsRequestHandler

    @Binds
    fun provideSettingsStateCommunicator(communicator: SettingsStateCommunicator.Base): SettingsStateCommunicator

    @Binds
    fun provideSettingsCommunicator(communicator: SettingsCommunicator.Base): SettingsCommunicator

    @Binds
    @FeatureScope
    fun provideSettingsCommunications(communicator: SettingsCommunications.Base): SettingsCommunications
}