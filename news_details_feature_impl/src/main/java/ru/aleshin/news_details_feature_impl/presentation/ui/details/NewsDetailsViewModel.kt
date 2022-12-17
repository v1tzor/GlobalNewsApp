package ru.aleshin.news_details_feature_impl.presentation.ui.details

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.FlowCollector
import ru.aleshin.core.managers.CoroutineManager
import ru.aleshin.core.platform.viewmodel.BaseViewModel
import ru.aleshin.core.platform.viewmodel.Init
import ru.aleshin.news_details_feature_impl.domain.entities.NewsDetailsEntity
import ru.aleshin.news_details_feature_impl.domain.interactor.DetailsInteractor
import ru.aleshin.news_details_feature_impl.navigations.NavigationManager
import ru.aleshin.news_details_feature_impl.presentation.ui.details.common.DetailsRequestHandler
import ru.aleshin.news_details_feature_impl.presentation.ui.details.common.DetailsUiState
import ru.aleshin.news_details_feature_impl.presentation.ui.details.communications.NewsDetailsCommunications
import ru.aleshin.news_details_feature_impl.presentation.ui.details.communications.NewsDetailsCommunicationsCollect
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author Stanislav Aleshin on 15.10.2022.
 */
internal class NewsDetailsViewModel @Inject constructor(
    private val interactor: DetailsInteractor,
    private val requestHandler: DetailsRequestHandler,
    private val communications: NewsDetailsCommunications,
    private val navigationManager: NavigationManager,
    coroutineManager: CoroutineManager
) : BaseViewModel(coroutineManager), Init, NewsDetailsCommunicationsCollect {

    override fun init(itFirstStart: Boolean) {
        if (itFirstStart) requestHandler.handleFetchNews(viewModelScope) { interactor.fetchNews() }
    }

    fun pressShareButton() = runOnBackground {
        if (communications.fetchState() is DetailsUiState.News) {
            navigationManager.navigateToSharingScreen(communications.fetchDetailsNews().sourceUrl)
        }
    }

    fun pressSourceButton() = runOnBackground {
        if (communications.fetchState() is DetailsUiState.News) {
            navigationManager.navigateToNewsUrl(communications.fetchDetailsNews().sourceUrl)
        }
    }

    fun pressBackButton() = navigationManager.navigateToBackScreen()

    override fun collectState(
        lifecycleOwner: LifecycleOwner,
        collector: FlowCollector<DetailsUiState>
    ) = communications.collectState(lifecycleOwner, collector)

    override fun collectNewsDetails(
        lifecycleOwner: LifecycleOwner,
        collector: FlowCollector<NewsDetailsEntity>
    ) = communications.collectNewsDetails(lifecycleOwner, collector)

    class Factory @Inject constructor(viewModel: Provider<NewsDetailsViewModel>) :
        BaseViewModelFactory(viewModel)
}