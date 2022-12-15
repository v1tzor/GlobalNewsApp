package ru.aleshin.news_feature_impl.di.module

import dagger.Binds
import dagger.Module
import ru.aleshin.core.di.FeatureScope
import ru.aleshin.news_feature_impl.domain.common.NewsEitherWrapper
import ru.aleshin.news_feature_impl.domain.common.NewsErrorHandler
import ru.aleshin.news_feature_impl.domain.common.SettingsEitherToEntityMapper
import ru.aleshin.news_feature_impl.domain.interactors.DetailsInteractor
import ru.aleshin.news_feature_impl.domain.interactors.NewsInteractor

/**
 * @author Stanislav Aleshin on 18.10.2022.
 */

@Module
internal interface DomainModule {

    @Binds
    @FeatureScope
    fun provideNewsInteractor(newsInteractor: NewsInteractor.Base): NewsInteractor

    @Binds
    @FeatureScope
    fun provideDetailsInteractor(newsInteractor: DetailsInteractor.Base): DetailsInteractor

    @Binds
    fun provideSettingsEitherWrapper(wrapper: NewsEitherWrapper.Base): NewsEitherWrapper

    @Binds
    fun provideSettingsEitherToEntityMapper(mapper: SettingsEitherToEntityMapper.Base): SettingsEitherToEntityMapper

    @Binds
    fun provideNewsFeatureErrorHandler(handler: NewsErrorHandler.Base): NewsErrorHandler
}