package ru.aleshin.news_feature_impl.presentaiton.mappers

import ru.aleshin.core.common.Mapper
import ru.aleshin.news_feature_impl.domain.entites.NewsEntity
import ru.aleshin.news_feature_impl.presentaiton.models.NewsUi
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal interface NewsDomainToUiMapper : Mapper<NewsEntity, NewsUi> {

    class Base @Inject constructor() : NewsDomainToUiMapper {
        override fun map(input: NewsEntity) = NewsUi(
            title = input.title,
            content = input.content,
            source = input.source,
            sourceUrl = input.sourceUrl,
            imageUrl = input.image,
            publishedAt = input.publishedAt
        )
    }
}