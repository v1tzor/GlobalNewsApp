package ru.aleshin.news_settings_feature_api.domain

import ru.aleshin.core.common.Mapper

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
data class SettingsEntity(
    val language: Languages,
    val country: Countries,
    val defualtCategory: Categories
) {
    fun <O> map(mapper: Mapper<SettingsEntity, O>) = mapper.map(this)

    companion object {
        const val DEFUALT_ID = 1
        val DEFUALT_LANGUAGES = Languages.RUSSIAN
        val DEFUALT_COUNTRY = Countries.RUSSIAN_FEDERATION
        val DEFUALT_CATEGORY = Categories.BREAKING_NEWS
    }
}