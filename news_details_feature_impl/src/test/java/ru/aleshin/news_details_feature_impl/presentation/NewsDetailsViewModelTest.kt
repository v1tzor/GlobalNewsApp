package ru.aleshin.news_details_feature_impl.presentation

import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.aleshin.core.functional.Either
import ru.aleshin.core.managers.CoroutineManager
import ru.aleshin.core_db.exceptions.StorageReadException
import ru.aleshin.news_details_feature_impl.domain.entities.DetailsFailures
import ru.aleshin.news_details_feature_impl.domain.entities.NewsDetailsEntity
import ru.aleshin.news_details_feature_impl.domain.interactor.DetailsInteractor
import ru.aleshin.news_details_feature_impl.navigations.NavigationManager
import ru.aleshin.news_details_feature_impl.presentation.ui.details.NewsDetailsViewModel
import ru.aleshin.news_details_feature_impl.presentation.ui.details.common.DetailsRequestHandler
import ru.aleshin.news_details_feature_impl.presentation.ui.details.common.DetailsUiState
import ru.aleshin.news_details_feature_impl.presentation.ui.details.communications.NewsDetailsCommunications

/**
 * @author Stanislav Aleshin on 10.12.2022.
 */
internal class NewsDetailsViewModelTest {

    private lateinit var interactor: FakeDetailsInteractor
    private lateinit var requestHandler: DetailsRequestHandler
    private lateinit var communications: FakeNewsDetailsCommunications
    private lateinit var navigationManager: FakeNavigationManager
    private lateinit var coroutineManager: TestCoroutineManager
    private lateinit var viewModel: NewsDetailsViewModel

    @Before
    fun setUp() {
        interactor = FakeDetailsInteractor()
        communications = FakeNewsDetailsCommunications()
        navigationManager = FakeNavigationManager()
        coroutineManager = TestCoroutineManager()
        requestHandler = DetailsRequestHandler.Base(communications, coroutineManager)

        viewModel = NewsDetailsViewModel(
            interactor,
            requestHandler,
            communications,
            navigationManager,
            coroutineManager
        )
    }

    @Test
    fun test_init_first_start_success() {
        communications.changedStateList.add(DetailsUiState.Empty)
        interactor.currentNews = NewsDetailsEntity("1", "1", "1", "1", "1")

        viewModel.init(true)

        assertEquals(1, interactor.fetchNewsCalledCount)

        assertEquals(2, communications.changedStateList.size)
        assertEquals(true, communications.changedStateList[0] is DetailsUiState.Empty)
        assertEquals(true, communications.changedStateList[1] is DetailsUiState.News)

        assertEquals(1, communications.changedNewsList.size)
        assertEquals(
            NewsDetailsEntity("1", "1", "1", "1", "1"),
            communications.changedNewsList[0]
        )
    }

    @Test
    fun test_init_first_start_with_error() {
        communications.changedStateList.add(DetailsUiState.Empty)
        interactor.expectingErrorWhileFetchNews(true)

        viewModel.init(true)

        assertEquals(1, interactor.fetchNewsCalledCount)

        assertEquals(2, communications.changedStateList.size)
        assertEquals(true, communications.changedStateList[0] is DetailsUiState.Empty)
        assertEquals(true, communications.changedStateList[1] is DetailsUiState.Error)

        assertEquals(0, communications.changedNewsList.size)
    }

    @Test
    fun test_init_second_start() {
        communications.changedStateList.add(DetailsUiState.Empty)
        communications.changedStateList.add(DetailsUiState.News)
        communications.changedNewsList.add(NewsDetailsEntity("1", "1", "1", "1", "1"))

        viewModel.init(false)

        assertEquals(0, interactor.fetchNewsCalledCount)

        assertEquals(2, communications.changedStateList.size)
        assertEquals(1, communications.changedNewsList.size)
    }

    @Test
    fun test_press_back_button() {
        viewModel.pressBackButton()

        assertEquals(1, navigationManager.navigateToBackCalledCount)
    }

    @Test
    fun test_press_source_button_success() {
        communications.changedStateList.add(DetailsUiState.News)
        communications.changedNewsList.add(NewsDetailsEntity("1", "1", "1", "1", "1"))

        viewModel.pressSourceButton()

        assertEquals(1, navigationManager.navigateToNewsUrlCalledCount)
    }

    @Test
    fun test_press_source_button_with_error() {
        communications.changedStateList.add(DetailsUiState.Error)
        communications.changedNewsList.add(NewsDetailsEntity("1", "1", "1", "1", "1"))

        viewModel.pressSourceButton()

        assertEquals(0, navigationManager.navigateToNewsUrlCalledCount)
    }

    @Test
    fun test_press_share_button_success() {
        communications.changedStateList.add(DetailsUiState.News)
        communications.changedNewsList.add(NewsDetailsEntity("1", "1", "1", "1", "1"))

        viewModel.pressShareButton()

        assertEquals(1, navigationManager.navigateToSharingScreenCalledCount)
    }

    @Test
    fun test_press_share_button_with_error() {
        communications.changedStateList.add(DetailsUiState.Error)
        communications.changedNewsList.add(NewsDetailsEntity("1", "1", "1", "1", "1"))

        viewModel.pressShareButton()

        assertEquals(0, navigationManager.navigateToSharingScreenCalledCount)
    }

    private class FakeDetailsInteractor : DetailsInteractor {

        var currentNews: NewsDetailsEntity? = null
        var fetchNewsCalledCount = 0

        private var errorWhileFetchNews = false

        fun expectingErrorWhileFetchNews(isError: Boolean) {
            errorWhileFetchNews = isError
        }

        override suspend fun fetchNews(): Either<DetailsFailures, NewsDetailsEntity> {
            fetchNewsCalledCount++
            return if (!errorWhileFetchNews) {
                Either.Right(checkNotNull(currentNews))
            } else {
                Either.Left(DetailsFailures.DataBaseException(StorageReadException()))
            }
        }
    }

    private class FakeNewsDetailsCommunications : NewsDetailsCommunications {

        val changedStateList = mutableListOf<DetailsUiState>()
        var readStateCalledCount = 0

        val changedNewsList = mutableListOf<NewsDetailsEntity>()
        var readNewsCalledCount = 0

        override fun showState(state: DetailsUiState) {
            changedStateList.add(state)
        }

        override fun showDetailsNews(news: NewsDetailsEntity) {
            changedNewsList.add(news)
        }

        override suspend fun fetchDetailsNews(): NewsDetailsEntity {
            readNewsCalledCount++
            return changedNewsList.last()
        }

        override suspend fun fetchState(): DetailsUiState {
            readStateCalledCount++
            return changedStateList.last()
        }

        override fun collectState(
            lifecycleOwner: LifecycleOwner,
            collector: FlowCollector<DetailsUiState>
        ) = Unit

        override fun collectNewsDetails(
            lifecycleOwner: LifecycleOwner,
            collector: FlowCollector<NewsDetailsEntity>
        ) = Unit
    }

    private class FakeNavigationManager : NavigationManager {

        var navigateToBackCalledCount = 0

        var navigateToSharingScreenCalledCount = 0

        var navigateToNewsUrlCalledCount = 0

        override fun navigateToBackScreen() {
            navigateToBackCalledCount++
        }

        override fun navigateToSharingScreen(newsUrl: String) {
            navigateToSharingScreenCalledCount++
        }

        override fun navigateToNewsUrl(newsUrl: String) {
            navigateToNewsUrlCalledCount++
        }
    }

    private class TestCoroutineManager : CoroutineManager.Abstract(
        backgroundDispatcher = TestCoroutineDispatcher(),
        mainDispatcher = TestCoroutineDispatcher()
    )
}