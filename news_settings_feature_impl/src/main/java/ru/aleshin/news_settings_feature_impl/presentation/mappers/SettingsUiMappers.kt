package ru.aleshin.news_settings_feature_impl.presentation.mappers

import ru.aleshin.core.common.Mapper
import ru.aleshin.news_settings_feature_api.domain.SettingsEntity
import ru.aleshin.news_settings_feature_impl.presentation.models.SettingsUi
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 26.10.2022.
 */

internal interface SettingsDomainToUiMapper : Mapper<SettingsEntity, SettingsUi> {

    class Base @Inject constructor() : SettingsDomainToUiMapper {
        override fun map(input: SettingsEntity) = SettingsUi(
            language = input.language,
            country = input.country,
            category = input.defualtCategory
        )
    }
}

internal interface SettingsUiToDomainMapper : Mapper<SettingsUi, SettingsEntity> {

    class Base @Inject constructor() : SettingsUiToDomainMapper {
        override fun map(input: SettingsUi) = SettingsEntity(
            language = input.language,
            country = input.country,
            defualtCategory = input.category
        )
    }
}