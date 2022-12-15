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
import ru.aleshin.news_details_feature_impl.presentation.ui.details.communications.NewsDetailsStateCommunicator

/**
 * @author Stanislav Aleshin on 10.12.2022.
 */
internal class NewsDetailsViewModelTest {

    private lateinit var interactor: FakeDetailsInteractor
    private lateinit var requestHandler: DetailsRequestHandler
    private lateinit var stateCommunicator: FakeNewsDetailsStateCommunicator
    private lateinit var navigationManager: FakeNavigationManager
    private lateinit var coroutineManager: TestCoroutineManager
    private lateinit var viewModel: NewsDetailsViewModel

    @Before
    fun setUp() {
        interactor = FakeDetailsInteractor()
        stateCommunicator = FakeNewsDetailsStateCommunicator()
        navigationManager = FakeNavigationManager()
        coroutineManager = TestCoroutineManager()
        requestHandler = DetailsRequestHandler.Base(stateCommunicator, coroutineManager)

        viewModel = NewsDetailsViewModel(
            interactor,
            requestHandler,
            stateCommunicator,
            navigationManager,
            coroutineManager
        )
    }

    @Test
    fun test_init_first_start_success() {
        stateCommunicator.changedStateList.add(DetailsUiState.Empty)
        interactor.currentNews = NewsDetailsEntity("1", "1", "1", "1", "1")

        viewModel.init(true)

        assertEquals(1, interactor.fetchNewsCalledCount)

        assertEquals(2, stateCommunicator.changedStateList.size)
        assertEquals(true, stateCommunicator.changedStateList[0] is DetailsUiState.Empty)
        assertEquals(true, stateCommunicator.changedStateList[1] is DetailsUiState.News)
    }

    @Test
    fun test_init_first_start_with_error() {
        stateCommunicator.changedStateList.add(DetailsUiState.Empty)
        interactor.expectingErrorWhileFetchNews(true)

        viewModel.init(true)

        assertEquals(1, interactor.fetchNewsCalledCount)

        assertEquals(2, stateCommunicator.changedStateList.size)
        assertEquals(true, stateCommunicator.changedStateList[0] is DetailsUiState.Empty)
        assertEquals(true, stateCommunicator.changedStateList[1] is DetailsUiState.Error)
    }

    @Test
    fun test_init_second_start() {
        stateCommunicator.changedStateList.add(DetailsUiState.Empty)
        stateCommunicator.changedStateList.add(
            DetailsUiState.News(NewsDetailsEntity("1", "1", "1", "1", "1"))
        )

        viewModel.init(false)

        assertEquals(0, interactor.fetchNewsCalledCount)
        assertEquals(2, stateCommunicator.changedStateList.size)
    }

    @Test
    fun test_press_back_button() {
        viewModel.pressBackButton()

        assertEquals(true, navigationManager.isNavigateToBackScreen)
        assertEquals(1, navigationManager.navigateToBackCalledCount)
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

    private class FakeNewsDetailsStateCommunicator : NewsDetailsStateCommunicator {

        val changedStateList = mutableListOf<DetailsUiState>()
        var readCalledCount = 0

        override fun update(value: DetailsUiState) {
            changedStateList.add(value)
        }

        override suspend fun read(): DetailsUiState {
            readCalledCount++
            return changedStateList.last()
        }

        override fun collect(
            lifecycleOwner: LifecycleOwner,
            collector: FlowCollector<DetailsUiState>
        ) = Unit
    }

    private class FakeNavigationManager : NavigationManager {

        var isNavigateToBackScreen = false
        var navigateToBackCalledCount = 0

        override fun navigateToBackScreen() {
            navigateToBackCalledCount++
            isNavigateToBackScreen = true
        }
    }

    private class TestCoroutineManager : CoroutineManager.Abstract(
        backgroundDispatcher = TestCoroutineDispatcher(),
        mainDispatcher = TestCoroutineDispatcher()
    )
}