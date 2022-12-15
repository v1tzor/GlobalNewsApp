package ru.aleshin.news_settings_feature_impl.data

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.aleshin.core.exceptions.DataBaseException
import ru.aleshin.core_db.exceptions.StorageReadException
import ru.aleshin.core_db.exceptions.StorageUpdateException
import ru.aleshin.core_db.settings.SettingsLocalDataSource
import ru.aleshin.core_db.settings.SettingsModel
import ru.aleshin.news_settings_feature_api.data.SettingsDataToDomainMapper
import ru.aleshin.news_settings_feature_api.data.SettingsDomainToDataMapper
import ru.aleshin.news_settings_feature_api.domain.Categories
import ru.aleshin.news_settings_feature_api.domain.Countries
import ru.aleshin.news_settings_feature_api.domain.Languages
import ru.aleshin.news_settings_feature_api.domain.SettingsEntity
import ru.aleshin.news_settings_feature_impl.data.repository.SettingsRepositoryImpl
import ru.aleshin.news_settings_feature_impl.domain.repository.SettingsRepository

/**
 * @author Stanislav Aleshin on 04.11.2022.
 */
internal class SettingsRepositoryTest {

    private lateinit var repository: SettingsRepository
    private lateinit var localDataSource: FakeSettingsLocalDataSource
    private lateinit var mapperToDomain: SettingsDataToDomainMapper
    private lateinit var mapperToDataMapper: SettingsDomainToDataMapper

    @Before
    fun setUp() {
        localDataSource = FakeSettingsLocalDataSource()
        mapperToDomain = SettingsDataToDomainMapper.Base()
        mapperToDataMapper = SettingsDomainToDataMapper.Base()
        repository = SettingsRepositoryImpl(localDataSource, mapperToDomain, mapperToDataMapper)
    }

    @Test
    fun test_fetch_settings_success() = runBlocking {
        localDataSource.currentSettings = SettingsModel(1, "ru", "ru", "breaking-news")

        val actual = repository.fetchSettings()
        val expected = SettingsEntity(
            Languages.RUSSIAN,
            Countries.RUSSIAN_FEDERATION,
            Categories.BREAKING_NEWS
        )

        assertEquals(expected, actual)
        assertEquals(1, localDataSource.fetchSettingsCount)
    }

    @Test(expected = DataBaseException::class)
    fun test_fetch_settings_failure(): Unit = runBlocking {
        localDataSource.expectingErrorWhileFetch(true)

        repository.fetchSettings()
    }

    @Test
    fun test_update_settings_success() = runBlocking {
        repository.updateSettings(
            SettingsEntity(
                Languages.RUSSIAN,
                Countries.RUSSIAN_FEDERATION,
                Categories.BREAKING_NEWS
            )
        )

        val actual = localDataSource.currentSettings
        val expected = SettingsModel(1, "ru", "ru", "breaking-news")

        assertEquals(expected, actual)
        assertEquals(1, localDataSource.updateSettingsCount)
    }

    @Test(expected = DataBaseException::class)
    fun test_update_settings_failure(): Unit = runBlocking {
        localDataSource.expectingErrorWhileUpdate(true)

        repository.updateSettings(
            SettingsEntity(
                Languages.RUSSIAN,
                Countries.RUSSIAN_FEDERATION,
                Categories.BREAKING_NEWS
            )
        )
    }

    private class FakeSettingsLocalDataSource : SettingsLocalDataSource {

        var currentSettings: SettingsModel? = null

        var fetchSettingsCount = 0
        var updateSettingsCount = 0

        private var errorWhileFetch = false
        private var errorWhileUpdate = false

        fun expectingErrorWhileFetch(isError: Boolean) {
            errorWhileFetch = isError
        }

        fun expectingErrorWhileUpdate(isError: Boolean) {
            errorWhileUpdate = isError
        }

        override suspend fun fetchSettings(): SettingsModel {
            fetchSettingsCount++
            return if (!errorWhileFetch) checkNotNull(currentSettings)
            else throw DataBaseException(StorageReadException())
        }

        override suspend fun updateSettings(data: SettingsModel) {
            updateSettingsCount++
            if (!errorWhileUpdate) currentSettings = data
            else throw DataBaseException(StorageUpdateException())
        }
    }
}