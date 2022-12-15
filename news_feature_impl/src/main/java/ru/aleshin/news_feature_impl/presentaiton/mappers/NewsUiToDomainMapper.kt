package ru.aleshin.news_feature_impl.presentaiton.mappers

import ru.aleshin.core.common.Mapper
import ru.aleshin.news_feature_impl.domain.entites.NewsEntity
import ru.aleshin.news_feature_impl.presentaiton.models.NewsUi
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 15.10.2022.
 */
internal interface NewsUiToDomainMapper : Mapper<NewsUi, NewsEntity> {

    class Base @Inject constructor() : NewsUiToDomainMapper {
        override fun map(input: NewsUi) = NewsEntity(
            title = input.title,
            content = input.content,
            source = input.source,
            sourceUrl = input.sourceUrl,
            image = input.imageUrl,
            publishedAt = input.publishedAt
        )
    }
}