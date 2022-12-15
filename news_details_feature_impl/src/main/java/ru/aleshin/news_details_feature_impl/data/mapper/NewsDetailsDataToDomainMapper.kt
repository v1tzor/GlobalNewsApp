package ru.aleshin.news_details_feature_impl.data.mapper

import ru.aleshin.core.common.Mapper
import ru.aleshin.core_db.details.NewsDetailsModel
import ru.aleshin.news_details_feature_impl.domain.entities.NewsDetailsEntity
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
internal interface NewsDetailsDataToDomainMapper : Mapper<NewsDetailsModel, NewsDetailsEntity> {

    class Base @Inject constructor() : NewsDetailsDataToDomainMapper {
        override fun map(input: NewsDetailsModel) = NewsDetailsEntity(
            content = input.content,
            source = input.source,
            sourceUrl = input.sourceUrl,
            imageUrl = input.imageUrl,
            publishedAt = input.publishedAt
        )
    }
}