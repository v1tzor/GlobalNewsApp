package ru.aleshin.news_feature_impl.data.mappers

import ru.aleshin.core.common.Mapper
import ru.aleshin.core_db.details.NewsDetailsModel
import ru.aleshin.news_feature_impl.domain.entites.NewsEntity
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
internal interface NewsDetailsDomainToDataMapper : Mapper<NewsEntity, NewsDetailsModel> {

    class Base @Inject constructor() : NewsDetailsDomainToDataMapper {

        override fun map(input: NewsEntity) = NewsDetailsModel(
            content = input.content,
            source = input.source,
            sourceUrl = input.sourceUrl,
            imageUrl = input.image,
            publishedAt = input.publishedAt
        )
    }
}