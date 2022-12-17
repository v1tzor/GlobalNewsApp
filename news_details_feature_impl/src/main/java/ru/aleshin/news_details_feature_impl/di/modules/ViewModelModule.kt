package ru.aleshin.news_details_feature_impl.di.modules

import dagger.Binds
import dagger.Module
import ru.aleshin.core.di.FeatureScope
import ru.aleshin.core.platform.viewmodel.BaseViewModel
import ru.aleshin.news_details_feature_impl.presentation.ui.details.NewsDetailsViewModel
import ru.aleshin.news_details_feature_impl.presentation.ui.details.common.DetailsRequestHandler
import ru.aleshin.news_details_feature_impl.presentation.ui.details.communications.NewsDetailsCommunications
import ru.aleshin.news_details_feature_impl.presentation.ui.details.communications.NewsDetailsCommunicator
import ru.aleshin.news_details_feature_impl.presentation.ui.details.communications.NewsDetailsStateCommunicator

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
@Module
internal interface ViewModelModule {

    @Binds
    fun provideDetailsViewModel(viewModel: NewsDetailsViewModel): BaseViewModel

    @Binds
    fun provideDetailsViewModelFactory(factory: NewsDetailsViewModel.Factory): BaseViewModel.BaseViewModelFactory

    @Binds
    @FeatureScope
    fun provideDetailsCommunications(communications: NewsDetailsCommunications.Base): NewsDetailsCommunications

    @Binds
    fun provideDetailsStateCommunicator(communicator: NewsDetailsStateCommunicator.Base): NewsDetailsStateCommunicator

    @Binds
    fun provideDetailsCommunicator(communicator: NewsDetailsCommunicator.Base): NewsDetailsCommunicator

    @Binds
    fun provideDetailsRequestHandler(handler: DetailsRequestHandler.Base): DetailsRequestHandler
}