package ru.aleshin.news_feature_impl.presentaiton.ui.news

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.FlowCollector
import ru.aleshin.core.managers.CoroutineManager
import ru.aleshin.core.platform.viewmodel.BaseViewModel
import ru.aleshin.core.platform.viewmodel.Init
import ru.aleshin.news_feature_impl.di.holder.NewsComponentHolder
import ru.aleshin.news_feature_impl.domain.common.NewsErrorHandler
import ru.aleshin.news_feature_impl.domain.interactors.DetailsInteractor
import ru.aleshin.news_feature_impl.domain.interactors.NewsInteractor
import ru.aleshin.news_feature_impl.navigation.NavigationManager
import ru.aleshin.news_feature_impl.presentaiton.mappers.NewsUiToDomainMapper
import ru.aleshin.news_feature_impl.presentaiton.models.NewsUi
import ru.aleshin.news_feature_impl.presentaiton.ui.news.common.NewsRequestHandler
import ru.aleshin.news_feature_impl.presentaiton.ui.news.common.NewsUiState
import ru.aleshin.news_feature_impl.presentaiton.ui.news.communications.NewsCommunications
import ru.aleshin.news_feature_impl.presentaiton.ui.news.communications.NewsCommunicationsCollect
import ru.aleshin.news_settings_feature_api.domain.Categories
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal class NewsViewModel @Inject constructor(
    private val newsInteractor: NewsInteractor,
    private val detailsInteractor: DetailsInteractor,
    private val communications: NewsCommunications,
    private val requestHandler: NewsRequestHandler,
    private val navigationManager: NavigationManager,
    private val errorHandler: NewsErrorHandler,
    private val mapperToDomain: NewsUiToDomainMapper,
    coroutineManager: CoroutineManager
) : BaseViewModel(coroutineManager), Init, NewsCommunicationsCollect {

    private var currentCategory: Categories? = null

    override fun init(itFirstStart: Boolean) = runOnBackground {
        if (itFirstStart && communications.fetchState() is NewsUiState.Empty) {
            requestHandler.handleEitherSettings(viewModelScope) { newsInteractor.fetchSettings() }
            showPagingNews()
        }
    }

    fun pressNewsItem(news: NewsUi) = runOnBackground {
        detailsInteractor.saveNews(news.map(mapperToDomain))
        navigationManager.showDetailsScreen()
    }

    fun changedCategory(categories: Categories?) {
        if (currentCategory != null && currentCategory != categories) {
            currentCategory = categories
            showPagingNews()
        } else {
            currentCategory = categories
        }
    }

    fun changedNewsLoadState(loadState: LoadState) = runOnBackground {
        when (loadState) {
            is LoadState.NotLoading -> {
                val state = communications.fetchState()
                if (state !is NewsUiState.News && state !is NewsUiState.Init) {
                    communications.showState(NewsUiState.News)
                }
            }
            is LoadState.Loading -> {
                communications.showState(NewsUiState.Loading)
            }
            is LoadState.Error -> {
                val failure = errorHandler.handle(loadState.error)
                communications.showState(NewsUiState.Error(failure))
            }
        }
    }

    fun showPagingNews() = requestHandler.handleFlowPaging(viewModelScope) {
        newsInteractor.fetchPagingNews(currentCategory).cachedIn(viewModelScope)
    }

    override fun collectState(
        lifecycleOwner: LifecycleOwner,
        collector: FlowCollector<NewsUiState>
    ) = communications.collectState(lifecycleOwner, collector)

    override fun collectNews(
        lifecycleOwner: LifecycleOwner,
        collector: FlowCollector<PagingData<NewsUi>>
    ) = communications.collectNews(lifecycleOwner, collector)

    override fun onCleared() {
        super.onCleared()
        NewsComponentHolder.reset()
    }

    class Factory @Inject constructor(viewModel: Provider<NewsViewModel>) :
        BaseViewModelFactory(viewModel)
}