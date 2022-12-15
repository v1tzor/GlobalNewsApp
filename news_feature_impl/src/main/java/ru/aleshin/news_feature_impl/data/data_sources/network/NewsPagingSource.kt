package ru.aleshin.news_feature_impl.data.data_sources.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.aleshin.news_feature_impl.data.data_sources.network.service.NewsRequest
import ru.aleshin.news_feature_impl.data.mappers.ResponseResultMapper
import ru.aleshin.news_feature_impl.data.models.NewsModel

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal class NewsPagingSource @AssistedInject constructor(
    private val remoteDataSource: NewsRemoteDataSource,
    private val responseResultMapper: ResponseResultMapper<NewsModel>,
    @Assisted private val newsRequest: NewsRequest
) : PagingSource<Int, NewsModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsModel> {
        val currentPage = params.key ?: INITIAL_PAGE
        val response = remoteDataSource.fetchNews(newsRequest, currentPage)

        return responseResultMapper.mapToLoadResult(response, params, INITIAL_PAGE)
    }

    override fun getRefreshKey(state: PagingState<Int, NewsModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition)

        return anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }

    companion object {
        const val INITIAL_PAGE = 1
    }

    @AssistedFactory
    interface Factory {
        fun create(newsRequest: NewsRequest): NewsPagingSource
    }
}