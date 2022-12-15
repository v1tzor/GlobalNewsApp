package ru.aleshin.news_feature_impl.presentaiton.ui.news.common

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.aleshin.core.functional.Either
import ru.aleshin.core.managers.CoroutineManager
import ru.aleshin.news_feature_impl.domain.entites.NewsEntity
import ru.aleshin.news_feature_impl.domain.entites.NewsFailures
import ru.aleshin.news_feature_impl.presentaiton.mappers.PagingNewsDomainToUiMapper
import ru.aleshin.news_feature_impl.presentaiton.ui.news.communications.NewsCommunications
import ru.aleshin.news_settings_feature_api.domain.SettingsEntity
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal interface NewsRequestHandler {

    fun handleFlowPaging(
        scope: CoroutineScope,
        block: suspend () -> Flow<PagingData<NewsEntity>>
    )

    fun handleEitherSettings(
        scope: CoroutineScope,
        block: suspend () -> Either<NewsFailures, SettingsEntity>
    )

    class Base @Inject constructor(
        private val coroutineManager: CoroutineManager,
        private val communications: NewsCommunications,
        private val pagingDomainToUiMapper: PagingNewsDomainToUiMapper
    ) : NewsRequestHandler {

        override fun handleFlowPaging(
            scope: CoroutineScope,
            block: suspend () -> Flow<PagingData<NewsEntity>>
        ) = coroutineManager.runOnBackground(scope) {
            communications.updateNewsFlow(block.invoke().map { pagingDomainToUiMapper.map(it) })
        }

        override fun handleEitherSettings(
            scope: CoroutineScope,
            block: suspend () -> Either<NewsFailures, SettingsEntity>
        ) = coroutineManager.runOnBackground(scope) {
            when (val settingsEither = block.invoke()) {
                is Either.Right -> {
                    communications.showState(NewsUiState.Init(settingsEither.data.defualtCategory))
                }
                is Either.Left -> {
                    communications.showState(NewsUiState.Init(SettingsEntity.DEFUALT_CATEGORY))
                }
            }
        }
    }
}