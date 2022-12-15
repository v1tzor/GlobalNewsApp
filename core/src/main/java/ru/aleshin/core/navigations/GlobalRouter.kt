package ru.aleshin.core.navigations

import com.github.terrakok.cicerone.Router

/**
 * @author Stanislav Aleshin on 15.10.2022.
 */
open class GlobalRouter : Router() {

    fun showDialog(screen: DialogScreen) {
        executeCommands(ShowDialog(screen))
    }

    fun closeDialog(screen: DialogScreen) {
        executeCommands(CloseDialog(screen))
    }

    fun showBottomSheetFragment(screen: BottomSheetFragmentScreen) {
        executeCommands(ShowBottomSheetScreen(screen))
    }
}