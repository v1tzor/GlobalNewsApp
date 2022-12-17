package ru.aleshin.news_details_feature_impl.presentation.ui.details.common

import kotlinx.coroutines.CoroutineScope
import ru.aleshin.core.functional.Either
import ru.aleshin.core.managers.CoroutineManager
import ru.aleshin.news_details_feature_impl.domain.entities.DetailsFailures
import ru.aleshin.news_details_feature_impl.domain.entities.NewsDetailsEntity
import ru.aleshin.news_details_feature_impl.presentation.ui.details.communications.NewsDetailsCommunications
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 15.10.2022.
 */
internal interface DetailsRequestHandler {

    fun handleFetchNews(
        scope: CoroutineScope,
        block: suspend () -> Either<DetailsFailures, NewsDetailsEntity>
    )

    class Base @Inject constructor(
        private val communications: NewsDetailsCommunications,
        private val coroutineManager: CoroutineManager
    ) : DetailsRequestHandler {

        override fun handleFetchNews(
            scope: CoroutineScope,
            block: suspend () -> Either<DetailsFailures, NewsDetailsEntity>
        ) = coroutineManager.runOnBackground(scope) {
            when (val either = block.invoke()) {
                is Either.Right -> {
                    communications.showState(DetailsUiState.News)
                    communications.showDetailsNews(either.data)
                }
                is Either.Left -> {
                    communications.showState(DetailsUiState.Error)
                }
            }
        }
    }
}
