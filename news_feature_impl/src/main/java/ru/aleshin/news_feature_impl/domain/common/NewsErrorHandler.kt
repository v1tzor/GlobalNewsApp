package ru.aleshin.news_feature_impl.domain.common

import retrofit2.HttpException
import ru.aleshin.core.handlers.ErrorHandler
import ru.aleshin.core_db.exceptions.StorageExceptions
import ru.aleshin.news_feature_impl.domain.entites.NewsFailures
import java.io.InterruptedIOException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 14.10.2022.
 */
internal interface NewsErrorHandler : ErrorHandler<NewsFailures> {

    class Base @Inject constructor() : NewsErrorHandler {

        override fun handle(throwable: Throwable): NewsFailures = when (throwable) {
            is UnknownHostException -> NewsFailures.NetworkException
            is InterruptedIOException -> NewsFailures.NetworkException
            is HttpException -> NewsFailures.ServiceException
            is StorageExceptions -> NewsFailures.DataBaseException
            else -> NewsFailures.OtherException(throwable)
        }
    }
}
