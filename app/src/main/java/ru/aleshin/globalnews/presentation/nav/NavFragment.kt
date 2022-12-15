package ru.aleshin.globalnews.presentation.nav

import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import ru.aleshin.core.navigations.CustomAppNavigator
import ru.aleshin.core.platform.fragments.BaseFragmentWithViewModel
import ru.aleshin.globalnews.R
import ru.aleshin.globalnews.application.getApp
import ru.aleshin.globalnews.databinding.BottomNavFragmentBinding
import ru.aleshin.globalnews.di.annotation.NavCiceroneHolder
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 25.11.2022.
 */
class NavFragment : BaseFragmentWithViewModel<BottomNavFragmentBinding, NavViewModel>(
    BottomNavFragmentBinding::inflate
) {

    @Inject
    lateinit var factory: NavViewModel.Factory

    @Inject
    @NavCiceroneHolder
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator by lazy(LazyThreadSafetyMode.NONE) {
        CustomAppNavigator(requireActivity(), R.id.fragmentContainer, childFragmentManager)
    }

    override fun initDI() = requireContext().getApp().fetchAppComponent().inject(this)

    override fun initView(savedInstanceState: Bundle?) = with(requireViewBinding()) {
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.newsFragment -> { viewModel.pressNewsItem(); true }
                R.id.settingsFragment -> { viewModel.pressSettingsItem(); true }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun fetchViewModelClass() = NavViewModel::class.java

    override fun fetchViewModelFactory() = factory
}