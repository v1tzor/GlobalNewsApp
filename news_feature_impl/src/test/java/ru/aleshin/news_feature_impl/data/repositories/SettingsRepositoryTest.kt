package ru.aleshin.news_feature_impl.data.repositories

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.aleshin.core_db.exceptions.StorageReadException
import ru.aleshin.core_db.settings.SettingsLocalDataSource
import ru.aleshin.core_db.settings.SettingsModel
import ru.aleshin.news_feature_impl.domain.repositories.SettingsRepository
import ru.aleshin.news_settings_feature_api.data.SettingsDataToDomainMapper
import ru.aleshin.news_settings_feature_api.domain.Categories
import ru.aleshin.news_settings_feature_api.domain.Countries
import ru.aleshin.news_settings_feature_api.domain.Languages
import ru.aleshin.news_settings_feature_api.domain.SettingsEntity

/**
 * @author Stanislav Aleshin on 23.10.2022.
 */
internal class SettingsRepositoryTest {

    private lateinit var repository: SettingsRepository
    private lateinit var localDataSource: TestSettingsLocalDataSource
    private lateinit var mapper: SettingsDataToDomainMapper

    @Before
    fun setUp() {
        localDataSource = TestSettingsLocalDataSource()
        mapper = SettingsDataToDomainMapper.Base()
        repository = SettingsRepositoryImpl(localDataSource, mapper)
    }

    @Test
    fun test_fetch_settings_success() = runBlocking {
        localDataSource.currentData = SettingsModel(1, "ru", "ru", "breaking-news")
        localDataSource.settingsCalledCount = 0
        localDataSource.updateSettingsCalledCount = 0

        val actual = repository.fetchSettings()
        val expected = SettingsEntity(
            Languages.RUSSIAN,
            Countries.RUSSIAN_FEDERATION,
            Categories.BREAKING_NEWS
        )

        assertEquals(expected, actual)
        assertEquals(1, localDataSource.settingsCalledCount)
        assertEquals(0, localDataSource.updateSettingsCalledCount)
    }

    @Test(expected = StorageReadException::class)
    fun test_fetch_settings_failure() = runBlocking {
        localDataSource.currentData = null
        localDataSource.settingsCalledCount = 0
        localDataSource.updateSettingsCalledCount = 0

        repository.fetchSettings()

        assertEquals(1, localDataSource.settingsCalledCount)
        assertEquals(0, localDataSource.updateSettingsCalledCount)
    }
}

private class TestSettingsLocalDataSource : SettingsLocalDataSource {

    var currentData: SettingsModel? = null

    var settingsCalledCount = 0
    var updateSettingsCalledCount = 0

    override suspend fun fetchSettings(): SettingsModel {
        settingsCalledCount++
        return currentData ?: throw StorageReadException()
    }

    override suspend fun updateSettings(data: SettingsModel) {
        updateSettingsCalledCount++
        currentData = data
    }
}
