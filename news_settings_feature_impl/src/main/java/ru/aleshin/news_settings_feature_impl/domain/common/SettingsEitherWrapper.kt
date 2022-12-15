package ru.aleshin.news_settings_feature_impl.domain.common

import ru.aleshin.core.handlers.EitherFailureWrapper
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 24.10.2022.
 */

internal interface SettingsEitherWrapper : EitherFailureWrapper<SettingsFailures> {

    class Base @Inject constructor(errorHandler: SettingsErrorHandler) : SettingsEitherWrapper,
        EitherFailureWrapper.Abstract<SettingsFailures>(errorHandler)
}