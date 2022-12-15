package ru.aleshin.globalnews.presentation.activity

import ru.aleshin.core.managers.CoroutineManager
import ru.aleshin.core.platform.viewmodel.BaseViewModel
import ru.aleshin.core.platform.viewmodel.Init
import ru.aleshin.globalnews.navigation.AppNavigationManager
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author Stanislav Aleshin on 06.10.2022.
 */
class MainViewModel @Inject constructor(
    private val navigationManager: AppNavigationManager,
    coroutineManager: CoroutineManager
) : BaseViewModel(coroutineManager), Init {

    override fun init(itFirstStart: Boolean) {
        if (itFirstStart) {
            navigationManager.showNavFragment()
            navigationManager.selectNewsFeature()
        }
    }

    class Factory @Inject constructor(viewModel: Provider<MainViewModel>) :
        BaseViewModelFactory(viewModel)
}