package ru.aleshin.news_details_feature_impl.di.modules

import dagger.Binds
import dagger.Module
import ru.aleshin.core.di.FeatureScope
import ru.aleshin.news_details_feature_impl.data.mapper.NewsDetailsDataToDomainMapper
import ru.aleshin.news_details_feature_impl.data.repository.DetailsRepositoryImpl
import ru.aleshin.news_details_feature_impl.domain.repository.DetailsRepository

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
@Module
internal interface DataModule {

    @Binds
    @FeatureScope
    fun provideDetailsRepository(repository: DetailsRepositoryImpl): DetailsRepository

    @Binds
    @FeatureScope
    fun provideNewsDetailsDataToDomainMapper(mapper: NewsDetailsDataToDomainMapper.Base): NewsDetailsDataToDomainMapper
}