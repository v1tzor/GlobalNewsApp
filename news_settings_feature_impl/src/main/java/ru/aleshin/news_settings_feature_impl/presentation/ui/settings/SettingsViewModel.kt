package ru.aleshin.news_settings_feature_impl.presentation.ui.settings

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.FlowCollector
import ru.aleshin.core.managers.CoroutineManager
import ru.aleshin.core.platform.viewmodel.BaseViewModel
import ru.aleshin.core.platform.viewmodel.Init
import ru.aleshin.news_settings_feature_api.domain.Categories
import ru.aleshin.news_settings_feature_api.domain.Countries
import ru.aleshin.news_settings_feature_api.domain.Languages
import ru.aleshin.news_settings_feature_impl.domain.interactor.SettingsInteractor
import ru.aleshin.news_settings_feature_impl.navigation.SettingsNavigationManager
import ru.aleshin.news_settings_feature_impl.presentation.models.SettingsUi
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.common.SettingsRequestHandler
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.common.SettingsState
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.communications.SettingsCommunications
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.communications.SettingsCommunicationsCollect
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author Stanislav Aleshin on 24.10.2022.
 */
internal class SettingsViewModel @Inject constructor(
    private val interactor: SettingsInteractor,
    private val requestHandler: SettingsRequestHandler,
    private val communications: SettingsCommunications,
    private val navigationManager: SettingsNavigationManager,
    coroutineManager: CoroutineManager
) : BaseViewModel(coroutineManager), SettingsCommunicationsCollect, Init {

    override fun init(itFirstStart: Boolean) {
        if (itFirstStart) {
            requestHandler.handleEitherSettings(viewModelScope) { interactor.fetchSettings() }
        }
    }

    fun updateSettings(
        categories: Categories? = null,
        countries: Countries? = null,
        languages: Languages? = null
    ) = requestHandler.handleEitherSettings(viewModelScope) {
        interactor.updateSettings(categories, countries, languages)
    }

    fun pressCategorySettingsItem() {
        navigationManager.showCategoriesDialog()
    }

    fun pressLanguageSettingsItem() {
        navigationManager.showLanguagesDialog()
    }

    fun pressCountrySettingsItem() {
        navigationManager.showCountriesDialog()
    }

    override fun collectState(lifecycle: LifecycleOwner, collector: FlowCollector<SettingsState>) =
        communications.collectState(lifecycle, collector)

    override fun collectSettings(lifecycle: LifecycleOwner, collector: FlowCollector<SettingsUi>) =
        communications.collectSettings(lifecycle, collector)

    class Factory @Inject constructor(viewModel: Provider<SettingsViewModel>) :
        BaseViewModelFactory(viewModel)
}