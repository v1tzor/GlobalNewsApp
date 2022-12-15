package ru.aleshin.news_feature_impl.domain.entites

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal sealed class NewsFailures {

    object NetworkException : NewsFailures()

    object ServiceException : NewsFailures()

    object DataBaseException : NewsFailures()

    data class OtherException(val throwable: Throwable) : NewsFailures()
}