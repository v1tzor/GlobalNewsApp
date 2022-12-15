package ru.aleshin.news_settings_feature_impl.presentation.models

import ru.aleshin.core.common.Mapper
import ru.aleshin.news_settings_feature_api.domain.Categories
import ru.aleshin.news_settings_feature_api.domain.Countries
import ru.aleshin.news_settings_feature_api.domain.Languages
import ru.aleshin.news_settings_feature_api.domain.SettingsEntity

/**
 * @author Stanislav Aleshin on 26.10.2022.
 */
internal data class SettingsUi(
    val language: Languages = SettingsEntity.DEFUALT_LANGUAGES,
    val country: Countries = SettingsEntity.DEFUALT_COUNTRY,
    val category: Categories = SettingsEntity.DEFUALT_CATEGORY
) {
    fun <O> map(mapper: Mapper<SettingsUi, O>) = mapper.map(this)
}