package ru.aleshin.news_details_feature_impl.domain.entities

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
internal sealed class DetailsFailures {
    class DataBaseException(val exception: Throwable) : DetailsFailures()
    class OtherException(val exception: Throwable) : DetailsFailures()
}