package ru.aleshin.news_settings_feature_impl.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.aleshin.core.navigations.DialogScreen
import ru.aleshin.news_settings_feature_impl.presentation.ui.categories.CategoriesDialogFragment
import ru.aleshin.news_settings_feature_impl.presentation.ui.countries.CountriesDialogFragment
import ru.aleshin.news_settings_feature_impl.presentation.ui.languages.LanguagesDialogFragment
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.SettingsFragment

/**
 * @author Stanislav Aleshin on 24.10.2022.
 */
internal object SettingsRoutingScreens {

    fun fetchSettingsScreen() = FragmentScreen(NavigationKeys.SETTINGS_FRAGMENT) {
        SettingsFragment()
    }

    fun fetchCategoriesDialogScreen() = DialogScreen(NavigationKeys.CATEGORIES_DIALOG_FRAGMENT) {
        CategoriesDialogFragment()
    }

    fun fetchLanguagesDialogScreen() = DialogScreen(NavigationKeys.LANGUAGES_DIALOG_FRAGMENT) {
        LanguagesDialogFragment()
    }

    fun fetchCountriesDialogScreen() = DialogScreen(NavigationKeys.COUNTRIES_DIALOG_FRAGMENT) {
        CountriesDialogFragment()
    }
}

internal object NavigationKeys {
    const val SETTINGS_FRAGMENT = "SETTINGS_FRAGMENT"

    const val CATEGORIES_DIALOG_FRAGMENT = "CATEGORIES_DIALOG_FRAGMENT"
    const val LANGUAGES_DIALOG_FRAGMENT = "LANGUAGES_DIALOG_FRAGMENT"
    const val COUNTRIES_DIALOG_FRAGMENT = "COUNTRIES_DIALOG_FRAGMENT"
}