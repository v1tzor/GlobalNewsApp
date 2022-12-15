package ru.aleshin.news_feature_impl.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.test.* // ktlint-disable no-wildcard-imports
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.aleshin.core.functional.Either
import ru.aleshin.core.functional.EitherLeft
import ru.aleshin.core.managers.CoroutineManager
import ru.aleshin.news_feature_impl.domain.common.NewsErrorHandler
import ru.aleshin.news_feature_impl.domain.entites.NewsEntity
import ru.aleshin.news_feature_impl.domain.entites.NewsFailures
import ru.aleshin.news_feature_impl.domain.interactors.DetailsInteractor
import ru.aleshin.news_feature_impl.domain.interactors.NewsInteractor
import ru.aleshin.news_feature_impl.navigation.NavigationManager
import ru.aleshin.news_feature_impl.presentaiton.mappers.NewsDomainToUiMapper
import ru.aleshin.news_feature_impl.presentaiton.mappers.NewsUiToDomainMapper
import ru.aleshin.news_feature_impl.presentaiton.mappers.PagingNewsDomainToUiMapper
import ru.aleshin.news_feature_impl.presentaiton.models.NewsUi
import ru.aleshin.news_feature_impl.presentaiton.ui.news.NewsViewModel
import ru.aleshin.news_feature_impl.presentaiton.ui.news.common.NewsRequestHandler
import ru.aleshin.news_feature_impl.presentaiton.ui.news.common.NewsUiState
import ru.aleshin.news_feature_impl.presentaiton.ui.news.communications.NewsCommunications
import ru.aleshin.news_settings_feature_api.domain.Categories
import ru.aleshin.news_settings_feature_api.domain.Countries
import ru.aleshin.news_settings_feature_api.domain.Languages
import ru.aleshin.news_settings_feature_api.domain.SettingsEntity
import java.net.UnknownHostException

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
@OptIn(ExperimentalCoroutinesApi::class)
internal class NewsViewModelTest {

    private val testScope = TestScope()
    private val testDispatcher = StandardTestDispatcher(testScope.testScheduler)

    private lateinit var testDiffer: AsyncPagingDataDiffer<NewsUi>

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsInteractor: FakeNewsInteractor
    private lateinit var detailsInteractor: FakeDetailsInteractor
    private lateinit var communications: FakeNewsCommunications
    private lateinit var requestHandler: NewsRequestHandler
    private lateinit var navigationManager: FakeNavigationManager
    private lateinit var errorHandler: NewsErrorHandler
    private lateinit var mapperToDomain: NewsUiToDomainMapper
    private lateinit var mapperToUi: NewsDomainToUiMapper
    private lateinit var pagingMapperToUi: PagingNewsDomainToUiMapper
    private lateinit var coroutineManager: TestCoroutineManager

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        testDiffer = AsyncPagingDataDiffer(MyDiffCallback(), TestNewsListCallback(), Dispatchers.Main)

        newsInteractor = FakeNewsInteractor()
        detailsInteractor = FakeDetailsInteractor()
        communications = FakeNewsCommunications()
        coroutineManager = TestCoroutineManager()
        mapperToUi = NewsDomainToUiMapper.Base()
        pagingMapperToUi = PagingNewsDomainToUiMapper.Base(mapperToUi)
        requestHandler = NewsRequestHandler.Base(coroutineManager, communications, pagingMapperToUi)
        navigationManager = FakeNavigationManager()
        errorHandler = NewsErrorHandler.Base()
        mapperToDomain = NewsUiToDomainMapper.Base()

        viewModel = NewsViewModel(
            newsInteractor,
            detailsInteractor,
            communications,
            requestHandler,
            navigationManager,
            errorHandler,
            mapperToDomain,
            coroutineManager
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_init_first_start_success() = testScope.runTest {
        communications.changedStateList.add(NewsUiState.Empty)
        newsInteractor.currentSettings = SettingsEntity(
            Languages.RUSSIAN,
            Countries.RUSSIAN_FEDERATION,
            Categories.BREAKING_NEWS
        )
        newsInteractor.currentNews.add(NewsEntity("1", "1", "1", "1", "1", "1"))

        viewModel.init(true)

        assertEquals(1, communications.readStateCount)

        assertEquals(1, newsInteractor.fetchNewsCount)
        assertEquals(2, newsInteractor.fetchSettingsCount)

        assertEquals(2, communications.changedStateList.size)
        assertEquals(true, communications.changedStateList[0] is NewsUiState.Empty)
        assertEquals(true, communications.changedStateList[1] is NewsUiState.Init)

        viewModel.changedNewsLoadState(LoadState.Loading)

        assertEquals(3, communications.changedStateList.size)
        assertEquals(true, communications.changedStateList[2] is NewsUiState.Loading)

        assertEquals(2, communications.changedPagingList.size)

        val expected = listOf(NewsUi("1", "1", "1", "1", "1", "1"))

        communications.changedPagingList[1].test {
            testDiffer.submitData(awaitItem())

            val actual = testDiffer.snapshot().items

            assertEquals(expected, actual)

            viewModel.changedNewsLoadState(LoadState.NotLoading(false))
        }

        assertEquals(4, communications.changedStateList.size)
        assertEquals(true, communications.changedStateList[3] is NewsUiState.News)
    }

    @Test
    fun test_init_first_start_with_not_empty_state() {
        communications.changedStateList.add(NewsUiState.Empty)
        communications.changedStateList.add(NewsUiState.Init(Categories.BREAKING_NEWS))
        communications.changedStateList.add(NewsUiState.Loading)

        viewModel.init(true)

        assertEquals(1, communications.readStateCount)

        assertEquals(3, communications.changedStateList.size)

        assertEquals(0, newsInteractor.fetchNewsCount)
        assertEquals(0, newsInteractor.fetchSettingsCount)
    }

    @Test
    fun test_init_first_start_with_error_fetch_news() = testScope.runTest {
        communications.changedStateList.add(NewsUiState.Empty)
        newsInteractor.currentSettings = SettingsEntity(
            Languages.RUSSIAN,
            Countries.RUSSIAN_FEDERATION,
            Categories.BREAKING_NEWS
        )
        newsInteractor.expectingErrorWhileFetchNews(true)

        viewModel.init(true)

        assertEquals(1, communications.readStateCount)

        assertEquals(1, newsInteractor.fetchNewsCount)
        assertEquals(2, newsInteractor.fetchSettingsCount)

        assertEquals(2, communications.changedStateList.size)
        assertEquals(true, communications.changedStateList[0] is NewsUiState.Empty)
        assertEquals(true, communications.changedStateList[1] is NewsUiState.Init)

        viewModel.changedNewsLoadState(LoadState.Loading)

        assertEquals(3, communications.changedStateList.size)
        assertEquals(true, communications.changedStateList[2] is NewsUiState.Loading)

        assertEquals(2, communications.changedPagingList.size)
        communications.changedPagingList[1].test {
            testDiffer.submitData(awaitItem())

            assertEquals(emptyList<PagingData<NewsUi>>(), testDiffer.snapshot().items)

            // The Paging library does not allow correct testing of LoadState and PagingData
            val error = UnknownHostException()
            viewModel.changedNewsLoadState(LoadState.Error(error))
        }

        assertEquals(4, communications.changedStateList.size)
        assertEquals(true, communications.changedStateList[3] is NewsUiState.Error)
    }

    @Test
    fun test_init_first_start_with_error_fetch_settings() = testScope.runTest {
        communications.changedStateList.add(NewsUiState.Empty)
        newsInteractor.currentNews.add(NewsEntity("1", "1", "1", "1", "1", "1"))
        newsInteractor.expectingErrorWhileFetchSettings(true)

        viewModel.init(true)

        assertEquals(1, communications.readStateCount)

        assertEquals(1, newsInteractor.fetchNewsCount)
        assertEquals(2, newsInteractor.fetchSettingsCount)

        assertEquals(2, communications.changedStateList.size)
        assertEquals(true, communications.changedStateList[0] is NewsUiState.Empty)
        assertEquals(true, communications.changedStateList[1] is NewsUiState.Init)

        viewModel.changedNewsLoadState(LoadState.Loading)

        assertEquals(3, communications.changedStateList.size)
        assertEquals(true, communications.changedStateList[2] is NewsUiState.Loading)

        assertEquals(2, communications.changedPagingList.size)
        communications.changedPagingList[1].test {
            testDiffer.submitData(awaitItem())
            assertEquals(
                listOf(NewsUi("1", "1", "1", "1", "1", "1")),
                testDiffer.snapshot().items
            )
            viewModel.changedNewsLoadState(LoadState.NotLoading(false))
        }

        assertEquals(4, communications.changedStateList.size)
        assertEquals(true, communications.changedStateList[3] is NewsUiState.News)
    }

    @Test
    fun test_init_second_start_after_success_loading() {
        communications.changedStateList.add(NewsUiState.Empty)
        communications.changedStateList.add(NewsUiState.Init(Categories.BREAKING_NEWS))
        communications.changedStateList.add(NewsUiState.Loading)
        communications.changedStateList.add(NewsUiState.News)

        viewModel.init(false)

        assertEquals(4, communications.changedStateList.size)

        assertEquals(0, newsInteractor.fetchNewsCount)
        assertEquals(0, newsInteractor.fetchSettingsCount)
    }

    @Test
    fun test_press_news_item_with_success_save() {
        viewModel.pressNewsItem(NewsUi("1", "1", "1", "1", "1", "1"))

        assertEquals(1, detailsInteractor.savedNewsList.size)
        assertEquals(true, detailsInteractor.savedNewsList[0].isRight)

        assertEquals(1, navigationManager.showDetailsCalledCount)
        assertEquals(true, navigationManager.isShowDetailsScreen)
    }

    @Test
    fun test_press_news_item_with_error_save() {
        detailsInteractor.expectingErrorWhileSave(true)

        viewModel.pressNewsItem(NewsUi("1", "1", "1", "1", "1", "1"))

        assertEquals(1, detailsInteractor.savedNewsList.size)
        assertEquals(true, detailsInteractor.savedNewsList[0].isLeft)

        val actual = (detailsInteractor.savedNewsList[0] as Either.Left).data
        val expected = NewsFailures.DataBaseException

        assertEquals(expected, actual)

        assertEquals(1, navigationManager.showDetailsCalledCount)
        assertEquals(true, navigationManager.isShowDetailsScreen)
    }

    @Test
    fun test_changed_news_load_state_to_error() {
        communications.changedStateList.add(NewsUiState.Empty)
        communications.changedStateList.add(NewsUiState.Init(Categories.BREAKING_NEWS))
        communications.changedStateList.add(NewsUiState.Loading)
        communications.changedStateList.add(NewsUiState.News)

        viewModel.changedNewsLoadState(LoadState.Error(UnknownHostException()))

        assertEquals(5, communications.changedStateList.size)
        assertEquals(true, communications.changedStateList[4] is NewsUiState.Error)
    }

    @Test
    fun test_changed_news_load_state_to_loading() {
        communications.changedStateList.add(NewsUiState.Empty)
        communications.changedStateList.add(NewsUiState.Init(Categories.BREAKING_NEWS))
        communications.changedStateList.add(NewsUiState.Loading)
        communications.changedStateList.add(NewsUiState.News)

        viewModel.changedNewsLoadState(LoadState.Loading)

        assertEquals(5, communications.changedStateList.size)
        assertEquals(true, communications.changedStateList[4] is NewsUiState.Loading)
    }

    @Test
    fun test_changed_news_load_state_to_not_loading_with_correct_state() {
        communications.changedStateList.add(NewsUiState.Empty)
        communications.changedStateList.add(NewsUiState.Init(Categories.BREAKING_NEWS))
        communications.changedStateList.add(NewsUiState.Loading)

        viewModel.changedNewsLoadState(LoadState.NotLoading(false))

        assertEquals(4, communications.changedStateList.size)
        assertEquals(true, communications.changedStateList[3] is NewsUiState.News)
    }

    @Test
    fun test_changed_news_load_state_to_not_loading_with_news_state() {
        communications.changedStateList.add(NewsUiState.Empty)
        communications.changedStateList.add(NewsUiState.Init(Categories.BREAKING_NEWS))
        communications.changedStateList.add(NewsUiState.Loading)
        communications.changedStateList.add(NewsUiState.News)

        viewModel.changedNewsLoadState(LoadState.NotLoading(false))

        assertEquals(4, communications.changedStateList.size)
    }

    @Test
    fun test_changed_news_load_state_to_not_loading_with_init_state() {
        communications.changedStateList.add(NewsUiState.Empty)
        communications.changedStateList.add(NewsUiState.Init(Categories.BREAKING_NEWS))

        viewModel.changedNewsLoadState(LoadState.NotLoading(false))

        assertEquals(2, communications.changedStateList.size)
    }

    @Test
    fun test_show_paging_news() = testScope.runTest {
        communications.changedStateList.add(NewsUiState.News)
        newsInteractor.currentSettings = SettingsEntity(
            Languages.RUSSIAN,
            Countries.RUSSIAN_FEDERATION,
            Categories.BREAKING_NEWS
        )
        newsInteractor.currentNews.add(NewsEntity("1", "1", "1", "1", "1", "1"))

        val expected = NewsUi("1", "1", "1", "1", "1", "1")

        viewModel.showPagingNews()

        assertEquals(2, communications.changedPagingList.size)
        communications.changedPagingList[1].test {
            testDiffer.submitData(awaitItem())

            val actual = testDiffer.snapshot().items.last()

            assertEquals(expected, actual)
        }
    }

    private class FakeNewsInteractor : NewsInteractor {

        var currentNews = mutableListOf<NewsEntity>()
        var fetchNewsCount = 0

        var currentSettings: SettingsEntity? = null
        var fetchSettingsCount = 0

        private var errorWhileFetchSettings = false
        private var errorWhileFetchNews = false

        fun expectingErrorWhileFetchSettings(isError: Boolean) {
            errorWhileFetchSettings = isError
        }

        fun expectingErrorWhileFetchNews(isError: Boolean) {
            errorWhileFetchNews = isError
        }

        override suspend fun fetchPagingNews(category: Categories?): Flow<PagingData<NewsEntity>> {
            fetchNewsCount++
            fetchSettingsCount++
            return flow {
                // The Paging library does not allow correct testing of LoadState and PagingData
                emit(if (errorWhileFetchNews) PagingData.empty() else PagingData.from(currentNews))
            }
        }

        override suspend fun fetchSettings(): Either<NewsFailures, SettingsEntity> {
            fetchSettingsCount++
            return if (errorWhileFetchSettings) {
                Either.Left(data = NewsFailures.DataBaseException)
            } else {
                Either.Right(data = checkNotNull(currentSettings))
            }
        }
    }

    private class FakeDetailsInteractor : DetailsInteractor {

        val savedNewsList = mutableListOf<EitherLeft<NewsFailures>>()

        private var errorWhileSaveNews = false

        fun expectingErrorWhileSave(isError: Boolean) {
            errorWhileSaveNews = isError
        }

        override suspend fun saveNews(news: NewsEntity): EitherLeft<NewsFailures> {
            val either = if (errorWhileSaveNews) {
                Either.Left(data = NewsFailures.DataBaseException)
            } else {
                Either.Right(Unit)
            }
            savedNewsList.add(either)
            return either
        }
    }

    private class FakeNewsCommunications : NewsCommunications {

        val changedStateList = mutableListOf<NewsUiState>()
        var readStateCount = 0

        val changedPagingList = mutableListOf<Flow<PagingData<NewsUi>>>(flow { emit(PagingData.empty()) })

        override suspend fun updateNewsFlow(data: Flow<PagingData<NewsUi>>) {
            changedPagingList.add(data)
        }

        override fun showState(state: NewsUiState) {
            changedStateList.add(state)
        }

        override suspend fun fetchState(): NewsUiState {
            readStateCount++
            return changedStateList.last()
        }

        override fun collectState(
            lifecycleOwner: LifecycleOwner,
            collector: FlowCollector<NewsUiState>
        ) = Unit

        override fun collectNews(
            lifecycleOwner: LifecycleOwner,
            collector: FlowCollector<PagingData<NewsUi>>
        ) = Unit
    }

    private class FakeNavigationManager : NavigationManager {

        var isShowDetailsScreen = false
        var showDetailsCalledCount = 0

        override fun showDetailsScreen() {
            isShowDetailsScreen = true
            showDetailsCalledCount++
        }
    }

    private inner class TestCoroutineManager : CoroutineManager.Abstract(
        mainDispatcher = TestCoroutineDispatcher(),
        backgroundDispatcher = TestCoroutineDispatcher()
    )

    private class MyDiffCallback : DiffUtil.ItemCallback<NewsUi>() {
        override fun areItemsTheSame(oldItem: NewsUi, newItem: NewsUi): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NewsUi, newItem: NewsUi): Boolean {
            return oldItem == newItem
        }
    }
}

internal class TestNewsListCallback : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) = Unit
    override fun onRemoved(position: Int, count: Int) = Unit
    override fun onMoved(fromPosition: Int, toPosition: Int) = Unit
    override fun onChanged(position: Int, count: Int, payload: Any?) = Unit
}