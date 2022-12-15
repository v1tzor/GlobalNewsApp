package ru.aleshin.news_feature_impl.data.repositories

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.* // ktlint-disable no-wildcard-imports
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.aleshin.core.managers.DateManager
import ru.aleshin.news_feature_impl.data.data_sources.network.NewsPagingDataSource
import ru.aleshin.news_feature_impl.data.data_sources.network.service.NewsRequest
import ru.aleshin.news_feature_impl.data.mappers.NewsDataToDomainMapper
import ru.aleshin.news_feature_impl.data.models.NewsModel
import ru.aleshin.news_feature_impl.domain.entites.NewsEntity
import ru.aleshin.news_feature_impl.domain.repositories.NewsRepository
import ru.aleshin.news_feature_impl.presentation.TestNewsListCallback

/**
 * @author Stanislav Aleshin on 23.10.2022.
 */
@OptIn(ExperimentalCoroutinesApi::class)
internal class NewsRepositoryTest {

    private val testScope = TestScope()
    private val testDispatcher = StandardTestDispatcher(testScope.testScheduler)

    private lateinit var testDiffer: AsyncPagingDataDiffer<NewsEntity>

    private lateinit var repository: NewsRepository
    private lateinit var pagingDataSource: TestNewsPagingDataSource
    private lateinit var mapperToDomain: NewsDataToDomainMapper
    private lateinit var dateManager: FakeDateManager

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        testDiffer = AsyncPagingDataDiffer(MyDiffCallback(), TestNewsListCallback(), Dispatchers.Main)

        pagingDataSource = TestNewsPagingDataSource()
        dateManager = FakeDateManager()
        mapperToDomain = NewsDataToDomainMapper.Base(dateManager)

        repository = NewsRepositoryImpl(pagingDataSource, mapperToDomain)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_fetch_news_success() = testScope.runTest {
        pagingDataSource.currentData.add(NewsModel("1", "1", "1", "1", "1", "1", "1", "1"))

        val flow = repository.fetchNews("breaking-news", "ru", "ru")

        flow.test {
            testDiffer.submitData(awaitItem())

            val expected = listOf(NewsEntity("1", "1", "1", "1", "1", "1"))
            val actual = testDiffer.snapshot().items

            assertEquals(expected, actual)
            awaitComplete()
        }
    }

    @Test
    fun test_fetch_news_with_error() = testScope.runTest {
        pagingDataSource.expectingErrorFetchNews(true)

        val flow = repository.fetchNews("breaking-news", "ru", "ru")

        flow.test {
            testDiffer.submitData(awaitItem())

            // The Paging library does not allow correct testing of LoadState and PagingData
            assertEquals(emptyList<PagingData<NewsEntity>>(), testDiffer.snapshot().items)
            awaitComplete()
        }
    }

    private class TestNewsPagingDataSource : NewsPagingDataSource {

        val currentData = mutableListOf<NewsModel>()

        var newsCalledCount = 0

        private var errorWhileFetchNews = false

        fun expectingErrorFetchNews(isError: Boolean) {
            errorWhileFetchNews = isError
        }

        override fun fetchNews(newsRequest: NewsRequest): Flow<PagingData<NewsModel>> {
            newsCalledCount.inc()

            // The Paging library does not allow correct testing of LoadState and PagingData
            return flow { emit(if (!errorWhileFetchNews) PagingData.from(currentData) else PagingData.empty()) }
        }
    }

    private class FakeDateManager : DateManager {
        override fun parseIsoInstant(date: String) = date
    }

    private class MyDiffCallback : DiffUtil.ItemCallback<NewsEntity>() {
        override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
            return oldItem == newItem
        }
    }
}