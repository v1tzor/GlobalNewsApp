package ru.aleshin.news_settings_feature_impl.presentation

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.aleshin.news_settings_feature_api.domain.Categories
import ru.aleshin.news_settings_feature_api.domain.Countries
import ru.aleshin.news_settings_feature_api.domain.Languages
import ru.aleshin.news_settings_feature_api.domain.SettingsEntity
import ru.aleshin.news_settings_feature_impl.presentation.mappers.SettingsDomainToUiMapper
import ru.aleshin.news_settings_feature_impl.presentation.mappers.SettingsUiToDomainMapper
import ru.aleshin.news_settings_feature_impl.presentation.models.SettingsUi

/**
 * @author Stanislav Aleshin on 04.11.2022.
 */
internal class SettingsEntityMapperTest {

    private lateinit var mapperToUiMapper: SettingsDomainToUiMapper
    private lateinit var mapperToDomain: SettingsUiToDomainMapper

    @Before
    fun setUp() {
        mapperToUiMapper = SettingsDomainToUiMapper.Base()
        mapperToDomain = SettingsUiToDomainMapper.Base()
    }

    @Test
    fun test_map_entity_ui_to_domain() {
        val actual = mapperToDomain.map(
            SettingsUi(
                Languages.RUSSIAN,
                Countries.RUSSIAN_FEDERATION,
                Categories.WORLD
            )
        )
        val expected = SettingsEntity(
            Languages.RUSSIAN,
            Countries.RUSSIAN_FEDERATION,
            Categories.WORLD
        )

        assertEquals(expected, actual)
    }

    @Test
    fun test_map_entity_domain_to_ui() {
        val actual = mapperToUiMapper.map(
            SettingsEntity(
                Languages.RUSSIAN,
                Countries.RUSSIAN_FEDERATION,
                Categories.WORLD
            )
        )
        val expected = SettingsUi(
            Languages.RUSSIAN,
            Countries.RUSSIAN_FEDERATION,
            Categories.WORLD
        )

        assertEquals(expected, actual)
    }
}