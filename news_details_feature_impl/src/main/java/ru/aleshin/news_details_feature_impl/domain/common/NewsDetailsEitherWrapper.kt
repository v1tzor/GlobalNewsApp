package ru.aleshin.news_details_feature_impl.domain.common

import ru.aleshin.core.handlers.EitherFailureWrapper
import ru.aleshin.news_details_feature_impl.domain.entities.DetailsFailures
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
internal interface NewsDetailsEitherWrapper : EitherFailureWrapper<DetailsFailures> {

    class Base @Inject constructor(errorHandler: DetailsErrorHandler) : NewsDetailsEitherWrapper,
        EitherFailureWrapper.Abstract<DetailsFailures>(errorHandler)
}