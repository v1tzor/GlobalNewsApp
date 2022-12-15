package ru.aleshin.news_feature_impl.domain.common

import ru.aleshin.core.functional.Either
import ru.aleshin.news_feature_impl.domain.entites.NewsFailures
import ru.aleshin.news_settings_feature_api.domain.Categories
import ru.aleshin.news_settings_feature_api.domain.SettingsEntity
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal interface SettingsEitherToEntityMapper {

    fun map(either: Either<NewsFailures, SettingsEntity>, category: Categories?): SettingsEntity

    class Base @Inject constructor() : SettingsEitherToEntityMapper {

        override fun map(
            either: Either<NewsFailures, SettingsEntity>,
            category: Categories?
        ): SettingsEntity = when (either) {
            is Either.Right -> SettingsEntity(
                language = either.data.language,
                country = either.data.country,
                defualtCategory = category ?: either.data.defualtCategory
            )
            is Either.Left -> SettingsEntity(
                language = SettingsEntity.DEFUALT_LANGUAGES,
                country = SettingsEntity.DEFUALT_COUNTRY,
                defualtCategory = category ?: SettingsEntity.DEFUALT_CATEGORY
            )
        }
    }
}