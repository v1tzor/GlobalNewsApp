package ru.aleshin.news_settings_feature_impl.presentation

import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.aleshin.core.functional.Either
import ru.aleshin.core.managers.CoroutineManager
import ru.aleshin.core_db.exceptions.StorageReadException
import ru.aleshin.core_db.exceptions.StorageUpdateException
import ru.aleshin.news_settings_feature_api.domain.Categories
import ru.aleshin.news_settings_feature_api.domain.Countries
import ru.aleshin.news_settings_feature_api.domain.Languages
import ru.aleshin.news_settings_feature_api.domain.SettingsEntity
import ru.aleshin.news_settings_feature_impl.domain.common.SettingsFailures
import ru.aleshin.news_settings_feature_impl.domain.interactor.SettingsInteractor
import ru.aleshin.news_settings_feature_impl.navigation.SettingsNavigationManager
import ru.aleshin.news_settings_feature_impl.presentation.mappers.SettingsDomainToUiMapper
import ru.aleshin.news_settings_feature_impl.presentation.models.SettingsUi
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.SettingsViewModel
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.common.SettingsRequestHandler
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.common.SettingsState
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.communications.SettingsCommunications

/**
 * @author Stanislav Aleshin on 04.11.2022.
 */
internal class SettingsViewModelTest {

    private lateinit var interactor: FakeSettingsInteractor
    private lateinit var requestHandler: SettingsRequestHandler
    private lateinit var coroutineManager: TestCoroutineManager
    private lateinit var communications: FakeSettingsCommunications
    private lateinit var navigationManager: FakeNewsSettingsNavigationManager
    private lateinit var mapperToUi: FakeSettingsDomainToUiMapper
    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setUp() {
        interactor = FakeSettingsInteractor()

        communications = FakeSettingsCommunications()

        mapperToUi = FakeSettingsDomainToUiMapper()
        coroutineManager = TestCoroutineManager()
        requestHandler = SettingsRequestHandler.Base(coroutineManager, communications, mapperToUi)

        navigationManager = FakeNewsSettingsNavigationManager()

        viewModel = SettingsViewModel(
            interactor,
            requestHandler,
            communications,
            navigationManager,
            coroutineManager
        )
    }

    @Test
    fun test_init_first_start_success() {
        communications.changedStateList.add(SettingsState.Settings())
        interactor.currentSettings = SettingsEntity(
            Languages.RUSSIAN,
            Countries.RUSSIAN_FEDERATION,
            Categories.BREAKING_NEWS
        )

        viewModel.init(itFirstStart = true)

        assertEquals(1, interactor.fetchCalledList.size)
        assertEquals(true, interactor.fetchCalledList[0].isRight)

        assertEquals(2, communications.changedStateList.size)
        assertEquals(true, communications.changedStateList[0] is SettingsState.Settings)
        assertEquals(true, communications.changedStateList[1] is SettingsState.Settings)

        assertEquals(1, communications.changedSettingsList.size)
        assertEquals(
            SettingsUi(
                Languages.RUSSIAN,
                Countries.RUSSIAN_FEDERATION,
                Categories.BREAKING_NEWS
            ),
            communications.changedSettingsList[0]
        )

        assertEquals(0, communications.readSettingsCount)
        assertEquals(0, communications.readStateCount)
    }

    @Test
    fun test_init_first_start_with_error() {
        communications.changedStateList.add(SettingsState.Settings())
        interactor.expectingErrorFetchSettings(true)

        viewModel.init(itFirstStart = true)

        assertEquals(1, interactor.fetchCalledList.size)
        assertEquals(true, interactor.fetchCalledList[0].isLeft)

        assertEquals(0, mapperToUi.mapCalledList.size)

        assertEquals(2, communications.changedStateList.size)
        assertEquals(true, communications.changedStateList[0] is SettingsState.Settings)
        assertEquals(true, communications.changedStateList[1] is SettingsState.Error)

        assertEquals(0, communications.changedSettingsList.size)
    }

    @Test
    fun test_update_category_settings_success() {
        communications.changedStateList.add(SettingsState.Settings())
        communications.changedStateList.add(SettingsState.Settings())
        communications.changedSettingsList.add(
            SettingsUi(Languages.RUSSIAN, Countries.RUSSIAN_FEDERATION, Categories.BREAKING_NEWS)
        )
        interactor.currentSettings = SettingsEntity(
            Languages.RUSSIAN,
            Countries.RUSSIAN_FEDERATION,
            Categories.BREAKING_NEWS
        )

        viewModel.updateSettings(categories = Categories.SCIENCE)

        assertEquals(1, interactor.updateCalledList.size)
        assertEquals(true, interactor.updateCalledList[0].isRight)
        assertEquals(
            SettingsEntity(Languages.RUSSIAN, Countries.RUSSIAN_FEDERATION, Categories.SCIENCE),
            (interactor.updateCalledList[0] as Either.Right).data
        )

        assertEquals(3, communications.changedStateList.size)
        assertEquals(true, communications.changedStateList.last() is SettingsState.Settings)

        assertEquals(2, communications.changedSettingsList.size)
        assertEquals(
            SettingsUi(Languages.RUSSIAN, Countries.RUSSIAN_FEDERATION, Categories.SCIENCE),
            communications.changedSettingsList[1]
        )
    }

    @Test
    fun test_update_category_settings_error() {
        communications.changedStateList.add(SettingsState.Settings())
        communications.changedStateList.add(SettingsState.Settings())
        communications.changedSettingsList.add(
            SettingsUi(Languages.RUSSIAN, Countries.RUSSIAN_FEDERATION, Categories.BREAKING_NEWS)
        )
        interactor.expectingErrorUpdateSettings(true)

        viewModel.updateSettings(categories = Categories.SCIENCE)

        assertEquals(1, interactor.updateCalledList.size)
        assertEquals(true, interactor.updateCalledList[0].isLeft)

        assertEquals(1, communications.changedSettingsList.size)

        assertEquals(3, communications.changedStateList.size)
        assertEquals(true, communications.changedStateList.last() is SettingsState.Error)
    }

    @Test
    fun test_press_category_settings_item() {
        communications.changedStateList.add(SettingsState.Settings())
        communications.changedStateList.add(SettingsState.Settings())

        viewModel.pressCategorySettingsItem()

        assertEquals(true, navigationManager.isSuccessShowCategories)
        assertEquals(false, navigationManager.isSuccessShowCountries)
        assertEquals(false, navigationManager.isSuccessShowLanguages)

        assertEquals(2, communications.changedStateList.size)
    }

    @Test
    fun test_press_language_settings_item() {
        communications.changedStateList.add(SettingsState.Settings())
        communications.changedStateList.add(SettingsState.Settings())

        viewModel.pressLanguageSettingsItem()

        assertEquals(false, navigationManager.isSuccessShowCategories)
        assertEquals(false, navigationManager.isSuccessShowCountries)
        assertEquals(true, navigationManager.isSuccessShowLanguages)

        assertEquals(2, communications.changedStateList.size)
    }

    @Test
    fun test_press_country_settings_item() {
        communications.changedStateList.add(SettingsState.Settings())
        communications.changedStateList.add(SettingsState.Settings())

        viewModel.pressCountrySettingsItem()

        assertEquals(false, navigationManager.isSuccessShowCategories)
        assertEquals(true, navigationManager.isSuccessShowCountries)
        assertEquals(false, navigationManager.isSuccessShowLanguages)

        assertEquals(2, communications.changedStateList.size)
    }

    private class FakeSettingsInteractor : SettingsInteractor {

        var currentSettings: SettingsEntity? = null

        val fetchCalledList = mutableListOf<Either<SettingsFailures, SettingsEntity>>()
        val updateCalledList = mutableListOf<Either<SettingsFailures, SettingsEntity>>()

        private var errorWhileFetchNews = false
        private var errorWhileUpdateNews = false

        fun expectingErrorFetchSettings(isError: Boolean) {
            errorWhileFetchNews = isError
        }

        fun expectingErrorUpdateSettings(isError: Boolean) {
            errorWhileUpdateNews = isError
        }

        override suspend fun fetchSettings(): Either<SettingsFailures, SettingsEntity> {
            val either = if (errorWhileFetchNews || currentSettings == null) {
                Either.Left(data = SettingsFailures.DataBaseException(StorageReadException()))
            } else {
                Either.Right(data = checkNotNull(currentSettings))
            }
            fetchCalledList.add(either)
            return either
        }

        override suspend fun updateSettings(
            category: Categories?,
            country: Countries?,
            language: Languages?
        ): Either<SettingsFailures, SettingsEntity> {
            val either = if (errorWhileUpdateNews) {
                Either.Left(data = SettingsFailures.DataBaseException(StorageUpdateException()))
            } else {
                val newSettings = SettingsEntity(
                    defualtCategory = category ?: checkNotNull(currentSettings).defualtCategory,
                    country = country ?: checkNotNull(currentSettings).country,
                    language = language ?: checkNotNull(currentSettings).language
                )
                Either.Right(newSettings)
            }
            updateCalledList.add(either)
            return either
        }
    }

    private class FakeSettingsCommunications : SettingsCommunications {

        val changedStateList = mutableListOf<SettingsState>()
        val readStateCount = 0

        val changedSettingsList = mutableListOf<SettingsUi>()
        val readSettingsCount = 0

        override suspend fun showState(state: SettingsState) {
            changedStateList.add(state)
        }

        override suspend fun showSettings(settings: SettingsUi) {
            changedSettingsList.add(settings)
        }

        override suspend fun fetchState(): SettingsState {
            readStateCount.inc()
            return changedStateList.last()
        }

        override suspend fun fetchSettings(): SettingsUi {
            readSettingsCount.inc()
            return changedSettingsList.last()
        }

        override fun collectState(
            lifecycle: LifecycleOwner,
            collector: FlowCollector<SettingsState>
        ) = Unit

        override fun collectSettings(
            lifecycle: LifecycleOwner,
            collector: FlowCollector<SettingsUi>
        ) = Unit
    }

    private class FakeNewsSettingsNavigationManager : SettingsNavigationManager {

        var isSuccessShowCategories = false
        var isSuccessShowLanguages = false
        var isSuccessShowCountries = false

        override fun showCategoriesDialog() {
            isSuccessShowCategories = true
        }

        override fun showLanguagesDialog() {
            isSuccessShowLanguages = true
        }

        override fun showCountriesDialog() {
            isSuccessShowCountries = true
        }
    }

    private class FakeSettingsDomainToUiMapper : SettingsDomainToUiMapper {

        val mapCalledList = mutableListOf<SettingsUi>()

        override fun map(input: SettingsEntity) = SettingsUi(
            language = input.language,
            country = input.country,
            category = input.defualtCategory
        ).apply {
            mapCalledList.add(this)
        }
    }

    private class TestCoroutineManager : CoroutineManager.Abstract(
        backgroundDispatcher = TestCoroutineDispatcher(),
        mainDispatcher = TestCoroutineDispatcher()
    )
}