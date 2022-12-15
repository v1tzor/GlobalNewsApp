package ru.aleshin.news_settings_feature_api.data

import ru.aleshin.core.common.Mapper
import ru.aleshin.core_db.settings.SettingsModel
import ru.aleshin.news_settings_feature_api.domain.SettingsEntity
import ru.aleshin.news_settings_feature_api.domain.convertToCategory
import ru.aleshin.news_settings_feature_api.domain.convertToCountry
import ru.aleshin.news_settings_feature_api.domain.convertToLanguage

/**
 * @author Stanislav Aleshin on 24.10.2022.
 */
interface SettingsDataToDomainMapper : Mapper<SettingsModel, SettingsEntity> {

    class Base : SettingsDataToDomainMapper {
        override fun map(input: SettingsModel) = SettingsEntity(
            language = input.language.convertToLanguage(),
            country = input.country.convertToCountry(),
            defualtCategory = input.defualtCategory.convertToCategory()
        )
    }
}

interface SettingsDomainToDataMapper : Mapper<SettingsEntity, SettingsModel> {

    class Base : SettingsDomainToDataMapper {
        override fun map(input: SettingsEntity) = SettingsModel(
            id = SettingsEntity.DEFUALT_ID,
            language = input.language.data,
            country = input.country.data,
            defualtCategory = input.defualtCategory.data
        )
    }
}
