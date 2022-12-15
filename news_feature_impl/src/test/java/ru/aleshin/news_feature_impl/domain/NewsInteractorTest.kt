package ru.aleshin.news_feature_impl.domain

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.* // ktlint-disable no-wildcard-imports
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.aleshin.core.functional.Either
import ru.aleshin.core_db.exceptions.StorageReadException
import ru.aleshin.news_feature_impl.domain.common.NewsEitherWrapper
import ru.aleshin.news_feature_impl.domain.common.NewsErrorHandler
import ru.aleshin.news_feature_impl.domain.common.SettingsEitherToEntityMapper
import ru.aleshin.news_feature_impl.domain.entites.NewsEntity
import ru.aleshin.news_feature_impl.domain.entites.NewsFailures
import ru.aleshin.news_feature_impl.domain.interactors.NewsInteractor
import ru.aleshin.news_feature_impl.domain.repositories.NewsRepository
import ru.aleshin.news_feature_impl.domain.repositories.SettingsRepository
import ru.aleshin.news_feature_impl.presentation.TestNewsListCallback
import ru.aleshin.news_settings_feature_api.domain.Categories
import ru.aleshin.news_settings_feature_api.domain.Countries
import ru.aleshin.news_settings_feature_api.domain.Languages
import ru.aleshin.news_settings_feature_api.domain.SettingsEntity

/**
 * @author Stanislav Aleshin on 14.10.2022.
 */
@OptIn(ExperimentalCoroutinesApi::class)
internal class NewsInteractorTest {

    private val testScope = TestScope()
    private val testDispatcher = StandardTestDispatcher(testScope.testScheduler)

    private lateinit var testDiffer: AsyncPagingDataDiffer<NewsEntity>

    private lateinit var interactor: NewsInteractor
    private lateinit var newsRepository: FakeNewsRepository
    private lateinit var settingsRepository: TestSettingsRepository
    private lateinit var eitherWrapper: NewsEitherWrapper
    private lateinit var settingsErrorHandler: NewsErrorHandler
    private lateinit var settingsMapper: SettingsEitherToEntityMapper

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        testDiffer = AsyncPagingDataDiffer(MyDiffCallback(), TestNewsListCallback(), Dispatchers.Main)

        newsRepository = FakeNewsRepository()
        settingsRepository = TestSettingsRepository()
        settingsErrorHandler = NewsErrorHandler.Base()
        eitherWrapper = NewsEitherWrapper.Base(settingsErrorHandler)
        settingsMapper = SettingsEitherToEntityMapper.Base()

        interactor = NewsInteractor.Base(
            newsRepository,
            settingsRepository,
            eitherWrapper,
            settingsMapper
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_fetch_news_with_category_success() = testScope.runTest {
        settingsRepository.currentSettings = SettingsEntity(
            Languages.RUSSIAN,
            Countries.RUSSIAN_FEDERATION,
            Categories.BREAKING_NEWS
        )
        newsRepository.currentPagingDataList.add(NewsEntity("1", "1", "1", "1", "1", "1"))

        val flow = interactor.fetchPagingNews(Categories.BREAKING_NEWS)

        flow.test {
            testDiffer.submitData(awaitItem())

            val actual = testDiffer.snapshot().items
            val expected = listOf(NewsEntity("1", "1", "1", "1", "1", "1"))

            assertEquals(expected, actual)
            awaitComplete()
        }
    }

    @Test
    fun test_fetch_news_without_category_success() = testScope.runTest {
        settingsRepository.currentSettings = SettingsEntity(
            Languages.RUSSIAN,
            Countries.RUSSIAN_FEDERATION,
            Categories.BREAKING_NEWS
        )
        newsRepository.currentPagingDataList.add(NewsEntity("1", "1", "1", "1", "1", "1"))

        val flow = interactor.fetchPagingNews(null)

        flow.test {
            testDiffer.submitData(awaitItem())

            val actual = testDiffer.snapshot().items
            val expected = listOf(NewsEntity("1", "1", "1", "1", "1", "1"))

            assertEquals(expected, actual)
            awaitComplete()
        }
    }

    @Test
    fun test_fetch_news_with_error() = testScope.runTest {
        settingsRepository.currentSettings = SettingsEntity(
            Languages.RUSSIAN,
            Countries.RUSSIAN_FEDERATION,
            Categories.BREAKING_NEWS
        )
        newsRepository.expectingErrorFetchNews(true)

        val flow = interactor.fetchPagingNews(Categories.BREAKING_NEWS)

        flow.test {
            testDiffer.submitData(awaitItem())

            // The Paging library does not allow correct testing of LoadState and PagingData
            assertEquals(listOf<PagingData<NewsEntity>>(), testDiffer.snapshot().items)
            awaitComplete()
        }
    }

    @Test
    fun test_fetch_settings_success() = testScope.runTest {
        settingsRepository.currentSettings = SettingsEntity(
            Languages.RUSSIAN,
            Countries.RUSSIAN_FEDERATION,
            Categories.BREAKING_NEWS
        )

        val actual = interactor.fetchSettings()

        assertEquals(true, actual.isRight)
        assertEquals(
            SettingsEntity(
                Languages.RUSSIAN,
                Countries.RUSSIAN_FEDERATION,
                Categories.BREAKING_NEWS
            ),
            (actual as Either.Right).data
        )
    }

    @Test
    fun test_fetch_settings_with_error() = testScope.runTest {
        settingsRepository.expectingErrorFetchSettings(true)

        val actual = interactor.fetchSettings()

        assertEquals(true, actual.isLeft)
        assertEquals(true, (actual as Either.Left).data is NewsFailures.DataBaseException)
    }

    private class TestSettingsRepository : SettingsRepository {

        var currentSettings: SettingsEntity? = null
        var fetchSettingsCount = 0

        private var errorWhileSettings = false

        fun expectingErrorFetchSettings(error: Boolean) {
            errorWhileSettings = error
        }

        override suspend fun fetchSettings(): SettingsEntity {
            fetchSettingsCount++
            if (errorWhileSettings) throw StorageReadException()
            return checkNotNull(currentSettings)
        }
    }

    private class FakeNewsRepository : NewsRepository {

        val currentPagingDataList = mutableListOf<NewsEntity>()
        var fetchNewsCount = 0

        private var errorWhileFetchNews = false

        fun expectingErrorFetchNews(error: Boolean) {
            errorWhileFetchNews = error
        }

        override fun fetchNews(
            category: String,
            language: String,
            country: String
        ) = flow {
            // The Paging library does not allow correct testing of LoadState and PagingData
            emit(if (!errorWhileFetchNews) PagingData.from(currentPagingDataList) else PagingData.empty())
        }
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