package ru.aleshin.news_details_feature_impl.di.modules

import dagger.Binds
import dagger.Module
import ru.aleshin.core.di.FeatureScope
import ru.aleshin.news_details_feature_impl.domain.common.DetailsErrorHandler
import ru.aleshin.news_details_feature_impl.domain.common.NewsDetailsEitherWrapper
import ru.aleshin.news_details_feature_impl.domain.interactor.DetailsInteractor

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
@Module
internal interface DomainModule {

    @Binds
    @FeatureScope
    fun provideDetailsInteractor(interactor: DetailsInteractor.Base): DetailsInteractor

    @Binds
    fun provideNewsDetailsEitherWrapper(wrapper: NewsDetailsEitherWrapper.Base): NewsDetailsEitherWrapper

    @Binds
    fun provideDetailsErrorHandler(handler: DetailsErrorHandler.Base): DetailsErrorHandler
}