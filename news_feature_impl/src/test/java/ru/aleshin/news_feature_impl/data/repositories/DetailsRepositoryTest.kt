package ru.aleshin.news_feature_impl.data.repositories

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.aleshin.core_db.details.NewsDetailsLocalDataSource
import ru.aleshin.core_db.details.NewsDetailsModel
import ru.aleshin.news_feature_impl.data.mappers.NewsDetailsDomainToDataMapper
import ru.aleshin.news_feature_impl.domain.entites.NewsEntity
import ru.aleshin.news_feature_impl.domain.repositories.DetailsRepository

/**
 * @author Stanislav Aleshin on 22.10.2022.
 */
internal class DetailsRepositoryTest {

    private lateinit var repository: DetailsRepository
    private lateinit var localDataSource: TestNewsDetailsLocalDataSource
    private lateinit var mapperToData: NewsDetailsDomainToDataMapper

    @Before
    fun setUp() {
        localDataSource = TestNewsDetailsLocalDataSource()
        mapperToData = NewsDetailsDomainToDataMapper.Base()

        repository = DetailsRepositoryImpl(localDataSource, mapperToData)
    }

    @Test
    fun test_save_news_success() = runBlocking {
        localDataSource.currentData = NewsDetailsModel("1", "1", "1", "1", "1")

        repository.setNews(NewsEntity("2", "2", "2", "2", "2", "2"))

        assertEquals(1, localDataSource.saveNewsList.size)
        assertEquals(
            NewsDetailsModel("2", "2", "2", "2", "2"),
            localDataSource.saveNewsList[0]
        )
    }

    private class TestNewsDetailsLocalDataSource : NewsDetailsLocalDataSource {

        var currentData: NewsDetailsModel? = null

        val saveNewsList = mutableListOf<NewsDetailsModel>()

        override suspend fun save(news: NewsDetailsModel) {
            saveNewsList.add(news)
            currentData = news
        }

        override suspend fun read(): NewsDetailsModel {
            return checkNotNull(currentData) { "News details local storage is empty!" }
        }
    }
}
