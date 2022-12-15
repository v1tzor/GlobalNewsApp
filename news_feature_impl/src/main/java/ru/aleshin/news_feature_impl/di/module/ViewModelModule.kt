package ru.aleshin.news_feature_impl.di.module

import dagger.Binds
import dagger.Module
import ru.aleshin.core.di.FeatureScope
import ru.aleshin.core.platform.viewmodel.BaseViewModel
import ru.aleshin.news_feature_impl.navigation.NavigationManager
import ru.aleshin.news_feature_impl.presentaiton.ui.news.NewsViewModel
import ru.aleshin.news_feature_impl.presentaiton.ui.news.common.NewsRequestHandler
import ru.aleshin.news_feature_impl.presentaiton.ui.news.communications.NewsCommunications
import ru.aleshin.news_feature_impl.presentaiton.ui.news.communications.NewsStateCommunicator
import ru.aleshin.news_feature_impl.presentaiton.ui.news.communications.PagingNewsCommunicator

/**
 * @author Stanislav Aleshin on 22.10.2022.
 */
@Module
internal interface ViewModelModule {

    @Binds
    fun provideNavigationManager(manager: NavigationManager.Base): NavigationManager

    @Binds
    fun provideNewsViewModel(viewModel: NewsViewModel): BaseViewModel

    @Binds
    fun provideNewsViewModelFactory(factory: NewsViewModel.Factory): BaseViewModel.BaseViewModelFactory

    @Binds
    @FeatureScope
    fun provideNewsCommunications(communications: NewsCommunications.Base): NewsCommunications

    @Binds
    fun provideNewsStateCommunicator(communicator: NewsStateCommunicator.Base): NewsStateCommunicator

    @Binds
    fun providePagingNewsCommunicator(communicator: PagingNewsCommunicator.Base): PagingNewsCommunicator

    @Binds
    fun provideNewsRequestHandler(handler: NewsRequestHandler.Base): NewsRequestHandler
}