package ru.aleshin.news_settings_feature_impl.presentation.ui.settings.communications

import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.FlowCollector
import ru.aleshin.news_settings_feature_impl.presentation.models.SettingsUi
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.common.SettingsState

/**
 * @author Stanislav Aleshin on 26.10.2022.
 */
internal interface SettingsCommunicationsCollect {
    fun collectState(lifecycle: LifecycleOwner, collector: FlowCollector<SettingsState>)
    fun collectSettings(lifecycle: LifecycleOwner, collector: FlowCollector<SettingsUi>)
}