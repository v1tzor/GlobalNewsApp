package ru.aleshin.news_settings_feature_impl.presentation.ui.languages

import android.os.Bundle
import android.view.View
import ru.aleshin.core.extensions.addRadioButton
import ru.aleshin.core.extensions.checkByTag
import ru.aleshin.core.extensions.checkedRadioButtonTag
import ru.aleshin.core.platform.dialogs.BaseDialogFragmentWithViewModel
import ru.aleshin.news_settings_feature_api.domain.Languages
import ru.aleshin.news_settings_feature_api.domain.convertToLanguage
import ru.aleshin.news_settings_feature_impl.databinding.LanguagesDialogFragmentBinding
import ru.aleshin.news_settings_feature_impl.di.holder.NewsSettingsComponentHolder
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.SettingsViewModel
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 27.11.2022.
 */
internal class LanguagesDialogFragment :
    BaseDialogFragmentWithViewModel<LanguagesDialogFragmentBinding, SettingsViewModel>(
        LanguagesDialogFragmentBinding::inflate
    ) {

    @Inject
    lateinit var factory: SettingsViewModel.Factory

    override fun initDI() = NewsSettingsComponentHolder.fetchComponent().inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) =
        with(requireViewBinding()) {
            super.onViewCreated(view, savedInstanceState)

            Languages.values().forEach { radioGroup.addRadioButton(it.title, it.data) }

            viewModel.collectSettings(viewLifecycleOwner) { settings ->
                radioGroup.checkByTag(settings.language.data)
            }

            radioGroup.setOnCheckedChangeListener { radioGroup, _ ->
                viewModel.updateSettings(
                    languages = radioGroup.checkedRadioButtonTag().convertToLanguage()
                )
            }

            positiveButton.setOnClickListener { dismiss() }
        }

    override fun fetchViewModelClass() = SettingsViewModel::class.java

    override fun fetchViewModelFactory() = factory
}