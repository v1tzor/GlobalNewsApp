package ru.aleshin.globalnews.presentation.activity

import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import ru.aleshin.core.navigations.CustomAppNavigator
import ru.aleshin.core.platform.activity.BaseActivity
import ru.aleshin.globalnews.R
import ru.aleshin.globalnews.application.getApp
import ru.aleshin.globalnews.databinding.ActivityMainBinding
import ru.aleshin.globalnews.di.annotation.GlobalCiceroneHolder
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 06.10.2022.
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    ActivityMainBinding::inflate
) {

    @Inject
    lateinit var factory: MainViewModel.Factory

    @Inject
    @GlobalCiceroneHolder
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator by lazy(LazyThreadSafetyMode.NONE) {
        CustomAppNavigator(this, R.id.globalContainer)
    }

    override fun setTheme() = setTheme(ru.aleshin.core_ui.R.style.Theme_App_GlobalNews)

    override fun initDI() = getApp().fetchAppComponent().inject(this)

    override fun initData(savedInstanceState: Bundle?) {
        viewModel.init(savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun fetchViewModelClass() = MainViewModel::class.java

    override fun fetchViewModelFactory() = factory
}
