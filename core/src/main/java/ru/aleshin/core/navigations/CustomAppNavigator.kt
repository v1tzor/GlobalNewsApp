package ru.aleshin.core.navigations

import android.transition.Scene
import android.transition.TransitionManager
import android.widget.TextView
import androidx.fragment.app.*
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.google.android.material.transition.MaterialContainerTransform
import ru.aleshin.core.R

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
}