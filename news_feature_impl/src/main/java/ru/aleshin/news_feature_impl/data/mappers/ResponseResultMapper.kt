package ru.aleshin.news_feature_impl.data.mappers

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import ru.aleshin.core.functional.ResponseResult
import ru.aleshin.news_feature_impl.data.data_sources.network.paging.PagingParamsHandler
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal interface ResponseResultMapper<T : Any> {

    fun mapToLoadResult(
        responseResult: ResponseResult<List<T>>,
        pagingParams: LoadParams<Int>,
        initialPage: Int = 1
    ): LoadResult<Int, T>

    class Base<T : Any> @Inject constructor(
        private val paramsHandler: PagingParamsHandler
    ) : ResponseResultMapper<T> {

        override fun mapToLoadResult(
            responseResult: ResponseResult<List<T>>,
            pagingParams: LoadParams<Int>,
            initialPage: Int
        ): LoadResult<Int, T> = when (responseResult) {
            is ResponseResult.Success.Data -> {
                val pageInfo =
                    paramsHandler.handlePage(pagingParams, responseResult.data.size, initialPage)
                LoadResult.Page(responseResult.data, pageInfo.prev, pageInfo.next)
            }
            is ResponseResult.Success.Empty -> {
                val pageInfo = paramsHandler.handlePage(pagingParams, 0, initialPage)
                LoadResult.Page(emptyList(), pageInfo.prev, pageInfo.next)
            }
            is ResponseResult.Error -> {
                LoadResult.Error(responseResult.throwable)
            }
        }
    }
}