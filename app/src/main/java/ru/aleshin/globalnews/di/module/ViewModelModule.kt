package ru.aleshin.globalnews.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ru.aleshin.globalnews.di.annotation.MainViewModels
import ru.aleshin.globalnews.di.annotation.NavViewModels
import ru.aleshin.globalnews.presentation.activity.MainViewModel
import ru.aleshin.globalnews.presentation.nav.NavViewModel

/**
 * @author Stanislav Aleshin on 25.11.2022.
 */
@Module
interface ViewModelModule {

    @Binds
    @MainViewModels
    fun provideMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @MainViewModels
    fun provideMainViewModelFactory(factory: MainViewModel.Factory): ViewModelProvider.Factory

    @Binds
    @NavViewModels
    fun provideNavViewModel(viewModel: NavViewModel): ViewModel

    @Binds
    @NavViewModels
    fun provideNavViewModelFactory(factory: NavViewModel.Factory): ViewModelProvider.Factory
}