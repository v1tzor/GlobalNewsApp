package ru.aleshin.news_favorites_feature_impl.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ru.aleshin.news_favorites_feature_impl.presentation.ui.FavoritesViewModel

/**
 * @author Stanislav Aleshin on 28.10.2022.
 */

@Module
internal interface ViewModelModule {

    @Binds
    fun provideFavoritesViewModel(viewModel: FavoritesViewModel): ViewModel

    @Binds
    fun provideFavoritesViewModelFactory(factory: FavoritesViewModel.Factory): ViewModelProvider.Factory
}