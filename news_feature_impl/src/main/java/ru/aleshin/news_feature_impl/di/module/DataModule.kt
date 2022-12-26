package ru.aleshin.news_feature_impl.di.module

import dagger.Binds
import dagger.Module
import ru.aleshin.core.di.FeatureScope
import ru.aleshin.core.handlers.ResponseHandler
import ru.aleshin.news_feature_impl.data.data_sources.network.NewsPagingDataSource
import ru.aleshin.news_feature_impl.data.data_sources.network.NewsRemoteDataSource
import ru.aleshin.news_feature_impl.data.data_sources.network.paging.PagingParamsHandler
import ru.aleshin.news_feature_impl.data.mappers.NewsDataToDomainMapper
import ru.aleshin.news_feature_impl.data.mappers.NewsDetailsDomainToDataMapper
import ru.aleshin.news_feature_impl.data.mappers.NewsResponseToModelMapper
import ru.aleshin.news_feature_impl.data.mappers.ResponseResultMapper
import ru.aleshin.news_feature_impl.data.models.NewsModel
import ru.aleshin.news_feature_impl.data.models.NewsResponse
import ru.aleshin.news_feature_impl.data.repositories.DetailsRepositoryImpl
import ru.aleshin.news_feature_impl.data.repositories.NewsRepositoryImpl
import ru.aleshin.news_feature_impl.data.repositories.SettingsRepositoryImpl
import ru.aleshin.news_feature_impl.domain.repositories.DetailsRepository
import ru.aleshin.news_feature_impl.domain.repositories.NewsRepository
import ru.aleshin.news_feature_impl.domain.repositories.SettingsRepository

/**
 * @author Stanislav Aleshin on 18.10.2022.
 */
@Module
internal interface DataModule {

    @Binds
    @FeatureScope
    fun provideNewsRepository(repository: NewsRepositoryImpl): NewsRepository

    @Binds
    @FeatureScope
    fun provideNewsPagingDataSource(dataSource: NewsPagingDataSource.Base): NewsPagingDataSource

    @Binds
    @FeatureScope
    fun provideNewsRemoteDataSource(dataSource: NewsRemoteDataSource.Base): NewsRemoteDataSource

    @Binds
    fun provideResponseResultMapper(mapper: ResponseResultMapper.Base<NewsModel>): ResponseResultMapper<NewsModel>

    @Binds
    fun provideNewsResponseHandler(handle: ResponseHandler.Base<NewsResponse>): ResponseHandler<NewsResponse>

    @Binds
    fun provideNewsResponseToModelMapper(mapper: NewsResponseToModelMapper.Base): NewsResponseToModelMapper

    @Binds
    fun providePagingParamsHandler(handle: PagingParamsHandler.Base): PagingParamsHandler

    @Binds
    @FeatureScope
    fun provideDetailsRepository(repository: DetailsRepositoryImpl): DetailsRepository

    @Binds
    @FeatureScope
    fun provideSettingsRepository(repository: SettingsRepositoryImpl): SettingsRepository

    @Binds
    fun provideNewsDataToDomainMapper(mapper: NewsDataToDomainMapper.Base): NewsDataToDomainMapper

    @Binds
    fun provideNewsDetailsDomainToDataMapper(mapper: NewsDetailsDomainToDataMapper.Base): NewsDetailsDomainToDataMapper
}