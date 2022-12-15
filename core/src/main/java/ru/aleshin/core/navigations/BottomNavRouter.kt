package ru.aleshin.core.navigations

import com.github.terrakok.cicerone.androidx.FragmentScreen

/**
 * @author Stanislav Aleshin on 15.10.2022.
 */
class BottomNavRouter : GlobalRouter() {

    fun selectBottomTabScreen(screen: FragmentScreen) {
        executeCommands(ChangeTabFragment(screen))
    }
}