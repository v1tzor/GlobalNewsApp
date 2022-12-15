package ru.aleshin.news_favorites_feature_impl.presentation.ui

import ru.aleshin.core.managers.CoroutineManager
import ru.aleshin.core.platform.viewmodel.BaseViewModel
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author Stanislav Aleshin on 28.10.2022.
 */

internal class FavoritesViewModel @Inject constructor(
    coroutineManager: CoroutineManager
) : BaseViewModel(coroutineManager) {

    class Factory @Inject constructor(viewModel: Provider<FavoritesViewModel>) :
        BaseViewModelFactory(viewModel)
}