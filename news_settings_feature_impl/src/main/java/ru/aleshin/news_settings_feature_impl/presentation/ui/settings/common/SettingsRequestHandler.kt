package ru.aleshin.news_settings_feature_impl.presentation.ui.settings.common

import kotlinx.coroutines.CoroutineScope
import ru.aleshin.core.functional.Either
import ru.aleshin.core.managers.CoroutineManager
import ru.aleshin.news_settings_feature_api.domain.SettingsEntity
import ru.aleshin.news_settings_feature_impl.domain.common.SettingsFailures
import ru.aleshin.news_settings_feature_impl.presentation.mappers.SettingsDomainToUiMapper
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.communications.SettingsCommunications
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 26.10.2022.
 */
internal interface SettingsRequestHandler {

    fun handleEitherSettings(
        scope: CoroutineScope,
        block: suspend () -> Either<SettingsFailures, SettingsEntity>
    )

    class Base @Inject constructor(
        private val coroutineManager: CoroutineManager,
        private val communications: SettingsCommunications,
        private val mapperToUi: SettingsDomainToUiMapper
    ) : SettingsRequestHandler {

        override fun handleEitherSettings(
            scope: CoroutineScope,
            block: suspend () -> Either<SettingsFailures, SettingsEntity>
        ) = coroutineManager.runOnBackground(scope) {
            when (val either = block.invoke()) {
                is Either.Right -> {
                    communications.showSettings(either.data.map(mapperToUi))
                    communications.showState(SettingsState.Settings())
                }
                is Either.Left -> {
                    communications.showState(SettingsState.Error())
                }
            }
        }
    }
}