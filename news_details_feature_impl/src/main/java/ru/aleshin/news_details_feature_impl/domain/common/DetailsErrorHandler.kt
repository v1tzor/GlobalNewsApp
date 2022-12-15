package ru.aleshin.news_details_feature_impl.domain.common

import ru.aleshin.core.handlers.ErrorHandler
import ru.aleshin.core_db.exceptions.StorageExceptions
import ru.aleshin.news_details_feature_impl.domain.entities.DetailsFailures
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
internal interface DetailsErrorHandler : ErrorHandler<DetailsFailures> {

    class Base @Inject constructor() : DetailsErrorHandler {

        override fun handle(throwable: Throwable) = when (throwable) {
            is StorageExceptions -> DetailsFailures.DataBaseException(throwable)
            else -> DetailsFailures.OtherException(throwable)
        }
    }
}