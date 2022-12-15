package ru.aleshin.news_settings_feature_impl.navigation

import ru.aleshin.core.navigations.BottomNavRouter
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 27.10.2022.
 */
internal interface SettingsNavigationManager {

    fun showCategoriesDialog()

    fun showLanguagesDialog()

    fun showCountriesDialog()

    class Base @Inject constructor(
        private val router: BottomNavRouter
    ) : SettingsNavigationManager {

        override fun showCategoriesDialog() {
            router.showDialog(SettingsRoutingScreens.fetchCategoriesDialogScreen())
        }

        override fun showLanguagesDialog() {
            router.showDialog(SettingsRoutingScreens.fetchLanguagesDialogScreen())
        }

        override fun showCountriesDialog() {
            router.showDialog(SettingsRoutingScreens.fetchCountriesDialogScreen())
        }
    }
}