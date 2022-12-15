package ru.aleshin.news_feature_impl.data.data_sources.network.paging

import androidx.paging.PagingSource.LoadParams
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal interface PagingParamsHandler {

    fun handlePage(params: LoadParams<Int>, countItems: Int, initialPage: Int = 1): PageInfo

    class Base @Inject constructor() : PagingParamsHandler {

        override fun handlePage(
            params: LoadParams<Int>,
            countItems: Int,
            initialPage: Int
        ): PageInfo {
            val currentPage = params.key ?: initialPage
            val pageSize = params.loadSize
            val nextPage = if (countItems == 0) null else currentPage.plus(1)
            val prevPage = if (currentPage == initialPage) null else currentPage.minus(1)

            return PageInfo(currentPage, nextPage, prevPage, pageSize)
        }
    }
}