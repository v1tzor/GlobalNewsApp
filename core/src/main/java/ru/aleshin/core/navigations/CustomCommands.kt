package ru.aleshin.core.navigations

import android.content.Intent
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.FragmentScreen

/**
 * @author Stanislav Aleshin on 25.11.2022.
 */

data class ShowDialog(val screen: DialogScreen) : Command

data class CloseDialog(val screen: DialogScreen) : Command

data class ShowBottomSheetScreen(val screen: BottomSheetFragmentScreen) : Command

data class ChangeTabFragment(val screen: FragmentScreen) : Command

data class ShowIntent(val intent: Intent) : Command