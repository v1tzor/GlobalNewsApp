package ru.aleshin.news_feature_impl.data.mappers

import ru.aleshin.core.common.Mapper
import ru.aleshin.news_feature_impl.data.models.NewsModel
import ru.aleshin.news_feature_impl.data.models.NewsResponse
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal interface NewsResponseToModelMapper : Mapper<NewsResponse, List<NewsModel>> {

    class Base @Inject constructor() : NewsResponseToModelMapper {

        override fun map(input: NewsResponse) = input.articles.map {
            NewsModel(
                title = it.title,
                description = it.description,
                content = it.content,
                sourceUrl = it.url,
                image = it.image,
                publishedAt = it.publishedAt,
                sourceName = it.source?.name,
                baseSourceUrl = it.source?.url
            )
        }
    }
}