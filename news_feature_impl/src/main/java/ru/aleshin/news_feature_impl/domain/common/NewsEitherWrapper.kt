package ru.aleshin.news_feature_impl.domain.common

import ru.aleshin.core.handlers.EitherFailureWrapper
import ru.aleshin.news_feature_impl.domain.entites.NewsFailures
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 14.10.2022.
 */
internal interface NewsEitherWrapper : EitherFailureWrapper<NewsFailures> {

    class Base @Inject constructor(errorHandler: NewsErrorHandler) : NewsEitherWrapper,
        EitherFailureWrapper.Abstract<NewsFailures>(errorHandler)
}
