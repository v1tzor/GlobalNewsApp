package ru.aleshin.core.navigations

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator

/**
 * @author Stanislav Aleshin on 15.10.2022.
 */
class CustomAppNavigator(
    activity: FragmentActivity,
    containerId: Int,
    fragmentManager: FragmentManager = activity.supportFragmentManager
) : AppNavigator(activity, containerId, fragmentManager) {

    override fun applyCommand(command: Command) = when (command) {
        is ShowDialog -> showDialog(command)
        is CloseDialog -> closeDialog(command)
        is ShowBottomSheetScreen -> showBottomSheetScreen(command)
        is ChangeTabFragment -> selectTab(command)
        is ShowIntent -> showIntent(command)
        else -> super.applyCommand(command)
    }

    private fun showBottomSheetScreen(command: ShowBottomSheetScreen) = with(command.screen) {
        createFragment(fragmentFactory).show(fragmentManager, screenKey)
    }

    private fun showDialog(command: ShowDialog) = with(command.screen) {
        createDialog(fragmentFactory).show(fragmentManager, screenKey)
    }

    private fun closeDialog(command: CloseDialog) = with(command.screen) {
        val dialog = fragmentManager.findFragmentByTag(screenKey)
        if (dialog is DialogFragment) dialog.dismiss()
    }

    private fun selectTab(command: ChangeTabFragment) {
        val tag = command.screen.screenKey
        val fragments = fragmentManager.fragments
        val fragment = fragmentManager.findFragmentByTag(tag)

        with(fragmentManager.beginTransaction()) {
            fragments.forEach { if (it.isVisible) hide(it) }
            if (fragment == null) {
                add(containerId, command.screen.createFragment(fragmentFactory), tag)
            }
            if (fragment != null) {
                show(fragment)
            }
            commitNow()
        }
    }

    private fun showIntent(command: ShowIntent) = activity.startActivity(command.intent)
}