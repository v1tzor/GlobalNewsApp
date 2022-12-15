package ru.aleshin.globalnews.presentation.nav

import ru.aleshin.core.managers.CoroutineManager
import ru.aleshin.core.platform.viewmodel.BaseViewModel
import ru.aleshin.globalnews.navigation.AppNavigationManager
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author Stanislav Aleshin on 25.11.2022.
 */
class NavViewModel @Inject constructor(
    private val navigationManager: AppNavigationManager,
    coroutineManager: CoroutineManager
) : BaseViewModel(coroutineManager) {

    fun pressNewsItem() = navigationManager.selectNewsFeature()

    fun pressSettingsItem() = navigationManager.selectSettingsFeature()

    class Factory @Inject constructor(viewModel: Provider<NavViewModel>) :
        BaseViewModelFactory(viewModel)
}