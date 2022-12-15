package ru.aleshin.news_settings_feature_impl.domain.common

/**
 * @author Stanislav Aleshin on 24.10.2022.
 */

internal sealed class SettingsFailures {

    data class DataBaseException(val throwable: Throwable) : SettingsFailures()

    data class OtherException(val throwable: Throwable) : SettingsFailures()
}