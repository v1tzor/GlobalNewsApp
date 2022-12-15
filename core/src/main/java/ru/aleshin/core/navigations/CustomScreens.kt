package ru.aleshin.core.navigations

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.Creator
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * @author Stanislav Aleshin on 25.11.2022.
 */

interface BottomSheetFragmentScreen : Screen {

    fun createFragment(factory: FragmentFactory): BottomSheetDialogFragment

    companion object {
        operator fun invoke(
            key: String? = null,
            fragmentCreator: Creator<FragmentFactory, BottomSheetDialogFragment>
        ) = object : BottomSheetFragmentScreen {
            override val screenKey = key ?: fragmentCreator::class.java.name
            override fun createFragment(factory: FragmentFactory) = fragmentCreator.create(factory)
        }
    }
}

interface DialogScreen : Screen {

    fun createDialog(factory: FragmentFactory): DialogFragment

    companion object {
        operator fun invoke(
            key: String? = null,
            fragmentCreator: Creator<FragmentFactory, DialogFragment>
        ) = object : DialogScreen {
            override val screenKey = key ?: fragmentCreator::class.java.name
            override fun createDialog(factory: FragmentFactory) = fragmentCreator.create(factory)
        }
    }
}