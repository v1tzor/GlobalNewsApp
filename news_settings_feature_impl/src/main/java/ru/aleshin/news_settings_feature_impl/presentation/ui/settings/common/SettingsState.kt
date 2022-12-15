package ru.aleshin.news_settings_feature_impl.presentation.ui.settings.common

import androidx.core.view.isVisible
import ru.aleshin.news_settings_feature_impl.databinding.SettingsFragmentBinding

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal sealed class SettingsState {

    abstract fun apply(viewBinding: SettingsFragmentBinding)

    abstract class Abstract(private val showErrorLayout: Boolean) : SettingsState() {
        override fun apply(viewBinding: SettingsFragmentBinding) {
            viewBinding.errorLayout.isVisible = showErrorLayout
        }
    }

    class Settings : Abstract(false)

    class Error : Abstract(true)
}
