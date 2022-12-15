package ru.aleshin.news_feature_impl.data.mappers

import ru.aleshin.core.common.Mapper
import ru.aleshin.core.managers.DateManager
import ru.aleshin.news_feature_impl.data.models.NewsModel
import ru.aleshin.news_feature_impl.domain.entites.NewsEntity
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 15.10.2022.
 */
internal interface NewsDataToDomainMapper : Mapper<NewsModel, NewsEntity> {

    class Base @Inject constructor(private val dateManager: DateManager) : NewsDataToDomainMapper {

        override fun map(input: NewsModel) = NewsEntity(
            title = checkNotNull(input.title),
            content = checkNotNull(input.description),
            source = checkNotNull(input.sourceName),
            sourceUrl = checkNotNull(input.sourceUrl),
            image = checkNotNull(input.image),
            publishedAt = dateManager.parseIsoInstant(checkNotNull(input.publishedAt))
        )
    }
}
