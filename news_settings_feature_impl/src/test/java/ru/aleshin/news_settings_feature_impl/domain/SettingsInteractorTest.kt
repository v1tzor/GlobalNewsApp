package ru.aleshin.news_settings_feature_impl.domain

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.aleshin.core.exceptions.DataBaseException
import ru.aleshin.core.functional.Either
import ru.aleshin.core_db.exceptions.StorageReadException
import ru.aleshin.core_db.exceptions.StorageUpdateException
import ru.aleshin.news_settings_feature_api.domain.Categories
import ru.aleshin.news_settings_feature_api.domain.Countries
import ru.aleshin.news_settings_feature_api.domain.Languages
import ru.aleshin.news_settings_feature_api.domain.SettingsEntity
import ru.aleshin.news_settings_feature_impl.domain.common.SettingsEitherWrapper
import ru.aleshin.news_settings_feature_impl.domain.common.SettingsErrorHandler
import ru.aleshin.news_settings_feature_impl.domain.common.SettingsFailures
import ru.aleshin.news_settings_feature_impl.domain.interactor.SettingsInteractor
import ru.aleshin.news_settings_feature_impl.domain.repository.SettingsRepository

/**
 * @author Stanislav Aleshin on 04.11.2022.
 */
internal class SettingsInteractorTest {

    private lateinit var interactor: SettingsInteractor
    private lateinit var repository: FakeSettingsRepository
    private lateinit var eitherWrapper: SettingsEitherWrapper
    private lateinit var errorHandler: SettingsErrorHandler

    @Before
    fun setUp() {
        repository = FakeSettingsRepository()
        errorHandler = SettingsErrorHandler.Base()
        eitherWrapper = SettingsEitherWrapper.Base(errorHandler)

        interactor = SettingsInteractor.Base(repository, eitherWrapper)
    }

    @Test
    fun test_fetch_settings_success() = runBlocking {
        repository.currentSettings = SettingsEntity(
            Languages.RUSSIAN,
            Countries.RUSSIAN_FEDERATION,
            Categories.BREAKING_NEWS
        )

        val actual = interactor.fetchSettings()

        assertEquals(1, repository.fetchSettingsCount)
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
    fun test_fetch_settings_failure() = runBlocking {
        repository.expectingErrorFetchSettings(true)

        val actual = interactor.fetchSettings()

        assertEquals(1, repository.fetchSettingsCount)
        assertEquals(true, actual.isLeft)
        assertEquals(true, (actual as Either.Left).data is SettingsFailures.DataBaseException)
    }

    @Test
    fun test_update_all_settings_success() = runBlocking {
        repository.currentSettings = SettingsEntity(
            Languages.RUSSIAN,
            Countries.RUSSIAN_FEDERATION,
            Categories.BREAKING_NEWS
        )

        val actual = interactor.updateSettings(
            Categories.SCIENCE,
            Countries.USA,
            Languages.ENGLISH
        )
        val expected = SettingsEntity(
            Languages.ENGLISH,
            Countries.USA,
            Categories.SCIENCE
        )

        assertEquals(1, repository.updateSettingsCount)
        assertEquals(true, actual.isRight)
        assertEquals(expected, (actual as Either.Right).data)
    }

    @Test
    fun test_update_categories_settings_success() = runBlocking {
        repository.currentSettings = SettingsEntity(
            Languages.RUSSIAN,
            Countries.RUSSIAN_FEDERATION,
            Categories.BREAKING_NEWS
        )

        val actual = interactor.updateSettings(category = Categories.SCIENCE, null, null)
        val expected = SettingsEntity(
            Languages.RUSSIAN,
            Countries.RUSSIAN_FEDERATION,
            Categories.SCIENCE
        )

        assertEquals(1, repository.updateSettingsCount)
        assertEquals(true, actual.isRight)
        assertEquals(expected, (actual as Either.Right).data)
    }

    @Test
    fun test_update_settings_failure() = runBlocking {
        repository.currentSettings = SettingsEntity(
            Languages.RUSSIAN,
            Countries.RUSSIAN_FEDERATION,
            Categories.BREAKING_NEWS
        )
        repository.expectingErrorUpdateSettings(true)

        val actual = interactor.updateSettings(
            Categories.SCIENCE,
            Countries.USA,
            Languages.ENGLISH
        )

        assertEquals(1, repository.updateSettingsCount)
        assertEquals(true, actual.isLeft)
        assertEquals(true, (actual as Either.Left).data is SettingsFailures.DataBaseException)
    }

    private class FakeSettingsRepository : SettingsRepository {

        var currentSettings: SettingsEntity? = null

        var fetchSettingsCount = 0
        var updateSettingsCount = 0

        private var errorWhileFetch = false
        private var errorWhileUpdate = false

        fun expectingErrorFetchSettings(isError: Boolean) {
            errorWhileFetch = isError
        }

        fun expectingErrorUpdateSettings(isError: Boolean) {
            errorWhileUpdate = isError
        }

        override suspend fun fetchSettings(): SettingsEntity {
            fetchSettingsCount++
            return if (!errorWhileFetch) checkNotNull(currentSettings)
            else throw StorageReadException()
        }

        override suspend fun updateSettings(settings: SettingsEntity) {
            updateSettingsCount++
            if (!errorWhileUpdate) currentSettings = settings
            else throw StorageUpdateException()
        }
    }
}