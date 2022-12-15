package ru.aleshin.news_feature_impl.presentaiton.mappers

import androidx.paging.PagingData
import androidx.paging.map
import ru.aleshin.core.common.Mapper
import ru.aleshin.news_feature_impl.domain.entites.NewsEntity
import ru.aleshin.news_feature_impl.presentaiton.models.NewsUi
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal interface PagingNewsDomainToUiMapper : Mapper<PagingData<NewsEntity>, PagingData<NewsUi>> {

    class Base @Inject constructor(
        private val mapperToUi: NewsDomainToUiMapper
    ) : PagingNewsDomainToUiMapper {

        override fun map(input: PagingData<NewsEntity>): PagingData<NewsUi> {
            return input.map { newsDomain -> newsDomain.map(mapperToUi) }
        }
    }
}
