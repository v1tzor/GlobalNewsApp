package ru.aleshin.globalnews.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.aleshin.globalnews.presentation.nav.NavFragment

/**
 * @author Stanislav Aleshin on 28.10.2022.
 */
object GlobalRoutingScreen {

    fun fetchNavScreen() = FragmentScreen(NavigationKeys.NAV_FRAGMENT) {
        NavFragment()
    }
}

object NavigationKeys {
    const val NAV_FRAGMENT = "NavFragment"
}