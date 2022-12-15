package ru.aleshin.news_details_feature_impl.domain

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.aleshin.core.functional.Either
import ru.aleshin.core_db.exceptions.StorageReadException
import ru.aleshin.news_details_feature_impl.domain.common.DetailsErrorHandler
import ru.aleshin.news_details_feature_impl.domain.common.NewsDetailsEitherWrapper
import ru.aleshin.news_details_feature_impl.domain.entities.DetailsFailures
import ru.aleshin.news_details_feature_impl.domain.entities.NewsDetailsEntity
import ru.aleshin.news_details_feature_impl.domain.interactor.DetailsInteractor
import ru.aleshin.news_details_feature_impl.domain.repository.DetailsRepository

/**
 * @author Stanislav Aleshin on 10.12.2022.
 */
internal class DetailsInteractorTest {

    private lateinit var repository: FakeDetailsRepository
    private lateinit var errorHandler: DetailsErrorHandler
    private lateinit var wrapper: NewsDetailsEitherWrapper
    private lateinit var interactor: DetailsInteractor

    @Before
    fun setUp() {
        repository = FakeDetailsRepository()
        errorHandler = DetailsErrorHandler.Base()
        wrapper = NewsDetailsEitherWrapper.Base(errorHandler)

        interactor = DetailsInteractor.Base(repository, wrapper)
    }

    @Test
    fun test_fetch_news_success() = runBlocking {
        repository.currentNews = NewsDetailsEntity("1", "1", "1", "1", "1")

        val actual = interactor.fetchNews()

        assertEquals(1, repository.fetchNewsCalledCount)

        assertEquals(true, actual.isRight)
        assertEquals(
            NewsDetailsEntity("1", "1", "1", "1", "1"),
            (actual as Either.Right).data
        )
    }

    @Test
    fun test_fetch_news_with_error() = runBlocking {
        repository.expectingErrorWhileFetchNews(true)

        val actual = interactor.fetchNews()

        assertEquals(1, repository.fetchNewsCalledCount)

        assertEquals(true, actual.isLeft)
        assertEquals(true, (actual as Either.Left).data is DetailsFailures.DataBaseException)
    }

    private class FakeDetailsRepository : DetailsRepository {

        var currentNews: NewsDetailsEntity? = null
        var fetchNewsCalledCount = 0

        private var errorWhileFetchNews = false

        fun expectingErrorWhileFetchNews(isError: Boolean) {
            errorWhileFetchNews = isError
        }

        override suspend fun fetchNews(): NewsDetailsEntity {
            fetchNewsCalledCount++
            return if (!errorWhileFetchNews) checkNotNull(currentNews) else throw StorageReadException()
        }
    }
}