package ru.aleshin.news_settings_feature_impl.presentation.ui.countries

import android.os.Bundle
import android.view.View
import ru.aleshin.core.extensions.addRadioButton
import ru.aleshin.core.extensions.checkByTag
import ru.aleshin.core.extensions.checkedRadioButtonTag
import ru.aleshin.core.platform.dialogs.BaseDialogFragmentWithViewModel
import ru.aleshin.news_settings_feature_api.domain.Countries
import ru.aleshin.news_settings_feature_api.domain.convertToCountry
import ru.aleshin.news_settings_feature_impl.databinding.CountriesDialogFragmentBinding
import ru.aleshin.news_settings_feature_impl.di.holder.NewsSettingsComponentHolder
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.SettingsViewModel
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 27.11.2022.
 */
internal class CountriesDialogFragment :
    BaseDialogFragmentWithViewModel<CountriesDialogFragmentBinding, SettingsViewModel>(
        CountriesDialogFragmentBinding::inflate
    ) {

    @Inject
    lateinit var factory: SettingsViewModel.Factory

    override fun initDI() = NewsSettingsComponentHolder.fetchComponent().inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) =
        with(requireViewBinding()) {
            super.onViewCreated(view, savedInstanceState)

            Countries.values().forEach { radioGroup.addRadioButton(it.title, it.data) }

            viewModel.collectSettings(viewLifecycleOwner) { settings ->
                radioGroup.checkByTag(settings.country.data)
            }

            radioGroup.setOnCheckedChangeListener { radioGroup, _ ->
                viewModel.updateSettings(
                    countries = radioGroup.checkedRadioButtonTag().convertToCountry()
                )
            }

            positiveButton.setOnClickListener { dismiss() }
        }

    override fun fetchViewModelClass() = SettingsViewModel::class.java

    override fun fetchViewModelFactory() = factory
}