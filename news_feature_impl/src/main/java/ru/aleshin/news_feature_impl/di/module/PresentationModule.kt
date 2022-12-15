package ru.aleshin.news_feature_impl.di.module

import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import dagger.Binds
import dagger.Module
import ru.aleshin.core.platform.adapter.BaseViewHolder
import ru.aleshin.news_feature_impl.presentaiton.mappers.NewsDomainToUiMapper
import ru.aleshin.news_feature_impl.presentaiton.mappers.NewsUiToDomainMapper
import ru.aleshin.news_feature_impl.presentaiton.mappers.PagingNewsDomainToUiMapper
import ru.aleshin.news_feature_impl.presentaiton.models.NewsUi
import ru.aleshin.news_feature_impl.presentaiton.ui.news.adapters.NewsAdapter
import ru.aleshin.news_feature_impl.presentaiton.ui.news.adapters.NewsLoadStateAdapter
import ru.aleshin.news_feature_impl.presentaiton.ui.news.adapters.NewsViewHolder

/**
 * @author Stanislav Aleshin on 18.10.2022.
 */
@Module
internal interface PresentationModule {

    @Binds
    fun provideNewsUiToDomainMapper(mapper: NewsUiToDomainMapper.Base): NewsUiToDomainMapper

    @Binds
    fun provideNewsDomainToUiMapper(mapper: NewsDomainToUiMapper.Base): NewsDomainToUiMapper

    @Binds
    fun providePagingNewsDomainToUiMapper(mapper: PagingNewsDomainToUiMapper.Base): PagingNewsDomainToUiMapper

    @Binds
    fun provideNewsPagingAdapter(adapter: NewsAdapter): PagingDataAdapter<NewsUi, NewsViewHolder>

    @Binds
    fun provideNewsLoadStatePagingAdapter(adapter: NewsLoadStateAdapter): LoadStateAdapter<BaseViewHolder<LoadState>>
}